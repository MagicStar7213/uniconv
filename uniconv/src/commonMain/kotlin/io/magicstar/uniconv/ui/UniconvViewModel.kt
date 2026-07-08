package io.magicstar.uniconv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import io.magicstar.uniconv.data.SettingsRepository
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Magnitude
import io.magicstar.uniconv.unit.model.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf

data class UniconvState(
    val magnitude: Magnitude? = null,
    val unit1: Unit? = null,
    val unit2: Unit? = null,
    val unit1Index: Int = 0,
    val unit2Index: Int = 1,
    val reference: List<Unit>? = null,
    val filteredUnits: List<Unit>? = null,
    val value1: Number = 0,
    val value2: Number = 0
)

sealed class UniconvIntent {
    class ChangeUnit(val unit: Unit, val type: Int): UniconvIntent()
    class ChangeMagnitude(val magnitude: Magnitude): UniconvIntent()
    class FilterUnits(val query: String): UniconvIntent()
    class Calculate(val units: Triple<Unit,Unit, Int>, val value: Number): UniconvIntent()
    class UpdateTextFieldValue(val newValue: Number, val unitType: Int): UniconvIntent()
}

class UniconvViewModel(val repo: SettingsRepository): ViewModel() {
    private val magnitudes = Magnitude::class.sealedSubclasses
    val magnitudesList = magnitudes.map { it.createInstance() }

    private val _state = MutableStateFlow(UniconvState())
    val state = _state.asStateFlow()

    init {
        val uiState = runBlocking {
            val magnitudeKeyValue = repo.getKey(repo.magnitudeKey).firstOrNull()
            val unit1KeyValue = repo.getKey(repo.unit1Key).firstOrNull()
            val unit2KeyValue = repo.getKey(repo.unit2Key).firstOrNull()

            val magnitude =
                if (magnitudeKeyValue != null) magnitudes.first { it.createInstance().id == magnitudeKeyValue }
                else magnitudes.first()

            val units = Unit::class.sealedSubclasses.filter { it.java.enclosingClass.kotlin == magnitude }.map { it.createInstance() }
            val unit1 =
                if (unit1KeyValue != null) units.first { it.abbreviation == unit1KeyValue }
                else units.first()
            val unit2 =
                if (unit2KeyValue != null) units.first { it.abbreviation == unit2KeyValue }
                else units[1]

            UniconvState(magnitude.createInstance(), unit1, unit2, units.indexOf(unit1), units.indexOf(unit2), units, units)
        }
        _state.update { uiState }
    }

    fun onIntent(intent: UniconvIntent) {
        when (intent) {
            is UniconvIntent.ChangeUnit -> changeUnit(intent.unit, intent.type)
            is UniconvIntent.ChangeMagnitude -> changeMagnitude(intent.magnitude)
            is UniconvIntent.FilterUnits -> filterUnits(intent.query)
            is UniconvIntent.Calculate -> calculate(intent.units, intent.value)
            is UniconvIntent.UpdateTextFieldValue -> updateTextFieldValue(intent.newValue, intent.unitType)
        }
    }

    fun updateTextFieldValue(newValue: Number, unitType: Int) {
        _state.update {
            if (unitType == 1)
                it.copy(value1 = newValue)
            else
                it.copy(value2 = newValue)
        }
    }

    fun changeUnit(unit: Unit, type: Int) {
        _state.update {
            if (type == 1)
                it.copy(unit1 = unit, unit1Index = _state.value.reference!!.indexOf(unit))
            else
                it.copy(unit2 = unit, unit2Index = _state.value.reference!!.indexOf(unit))
        }
        viewModelScope.launch {
            repo.setKey(if (type == 1) repo.unit1Key else repo.unit2Key, unit.abbreviation)
        }
    }

    fun changeMagnitude(magnitude: Magnitude) {
        _state.update { uiState ->
            uiState.copy(
                magnitude = magnitude,
                reference = Unit::class.sealedSubclasses.filter { it.java.enclosingClass.kotlin == magnitude::class }.map { it.createInstance() },
                unit1Index = if (uiState.unit1Index > uiState.reference!!.lastIndex) {
                    if (uiState.unit1Index > uiState.unit2Index) uiState.reference.lastIndex else uiState.reference.lastIndex - 1
                } else uiState.unit1Index,

                unit2Index = if (uiState.unit2Index > uiState.reference.lastIndex) {
                    if (uiState.unit2Index > uiState.unit1Index) uiState.reference.lastIndex else uiState.reference.lastIndex - 1
                } else uiState.unit2Index
            )
        }
        viewModelScope.launch {
            repo.setKey(repo.magnitudeKey, magnitude.id)
        }
        filterUnits("")
        changeUnit(_state.value.reference!![_state.value.unit1Index], 1)
        changeUnit(_state.value.reference!![_state.value.unit2Index], 2)
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

    fun calculate(units: Triple<Unit, Unit, Int>, value: Number) {
        val result = convert(value, units.first, units.second)
        _state.update {
            if (units.third == 1)
                _state.value.copy(value1 = result)
            else _state.value.copy(value2 = result)
        }
    }
}

class UniconvViewModelFactory(private val repo: SettingsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        if (modelClass.isSubclassOf(UniconvViewModel::class)) {
            @Suppress("UNCHECKED_CAST")
            return UniconvViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}