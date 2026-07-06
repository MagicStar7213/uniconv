package io.magicstar.uniconv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.magicstar.uniconv.data.SettingsRepository
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Magnitude
import io.magicstar.uniconv.unit.model.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString
import kotlin.reflect.full.createInstance

enum class UnitType {
    FROM,
    TO
}

data class UniconvState(
    val magnitude: Magnitude? = null,
    val fromUnit: Unit? = null,
    val toUnit: Unit? = null,
    val reference: List<Unit>? = null,
    val filteredUnits: List<Unit>? = null,
    val isCalculating: Boolean = false,
    val result: Number? = null
)

sealed class UniconvIntent {
    class ChangeUnit(val unit: Unit, val type: UnitType): UniconvIntent()
    class FilterUnits(val query: String): UniconvIntent()
    class Calculate(val units: Pair<Unit,Unit>, val value: Number): UniconvIntent()
}

class UniconvViewModel(val repo: SettingsRepository): ViewModel() {
    private val magnitudes = Magnitude::class.sealedSubclasses
    val magnitudesList = magnitudes.map { it.createInstance() }

    private val _state = MutableStateFlow(UniconvState())
    val state = _state.asStateFlow()

    init {
        val uiState = combine(
            repo.getKey(repo.magnitudeKey),
            repo.getKey(repo.fromUnitKey),
            repo.getKey(repo.toUnitKey)
        ) { magn, fU, toU ->
            val magnitude = if (magn != null) magnitudes.first { it.createInstance().id == magn } else magnitudes.first()
            val units = Unit::class.sealedSubclasses.filter { it.java.enclosingClass.kotlin == magnitude }.map { it as Unit }
            val fromUnit = if (fU != null) units.first { it.abbreviation == fU } else units.first()
            val toUnit = if (toU != null) units.first { it.abbreviation == toU } else units[1]
            UniconvState(magnitude.createInstance(), fromUnit, toUnit, units, units, _state.value.isCalculating, _state.value.result)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UniconvState()
        )
        _state.update { uiState.value }
    }

    fun onIntent(intent: UniconvIntent) {
        when (intent) {
            is UniconvIntent.ChangeUnit -> changeUnit(intent.unit, intent.type)
            is UniconvIntent.FilterUnits -> filterUnits(intent.query)
            is UniconvIntent.Calculate -> calculate(intent.units, intent.value)
        }
    }

    fun changeUnit(unit: Unit, unitType: UnitType) {
        _state.update {
            if (unitType== UnitType.FROM)
                it.copy(fromUnit = unit)
            else
                it.copy(toUnit = unit)
        }
        viewModelScope.launch {
            repo.setKey(if (unitType== UnitType.FROM) repo.fromUnitKey else repo.toUnitKey, unit.abbreviation)
        }
    }

    fun changeMagnitude(magnitude: Magnitude) {
        _state.update {
            it.copy(magnitude = magnitude)
        }
        viewModelScope.launch {
            repo.setKey(repo.magnitudeKey, magnitude.id)
        }
        filterUnits("")
    }

    fun filterUnits(query: String) {
        val initialList = (
            if (_state.value.magnitude != null)
                _state.value.magnitude!!::class
            else magnitudes.first()
        ).nestedClasses.map { it.createInstance() as Unit }
        val filteredList = if (query != "") initialList.filter {
            val unitName = runBlocking(Dispatchers.IO) { getString(it.name) }
            query.lowercase() in "$unitName (${it.abbreviation})"
        } else initialList
        _state.update { it.copy(filteredUnits = filteredList) }
    }

    fun calculate(units: Pair<Unit, Unit>, value: Number) {
        val result = convert(value, units.first, units.second)
        _state.update { it.copy(result = result) }
    }
}