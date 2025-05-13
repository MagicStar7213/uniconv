package io.magicstar.uniconv

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import io.magicstar.uniconv.unit.model.lengthUnits
import io.magicstar.uniconv.unit.model.speedUnits
import io.magicstar.uniconv.unit.model.surfaceUnits
import io.magicstar.uniconv.unit.model.temperatureUnits
import io.magicstar.uniconv.unit.model.timeUnits
import io.magicstar.uniconv.unit.model.volumeUnits
import io.magicstar.uniconv.unit.model.weightUnits
import org.jetbrains.compose.resources.stringResource

@Composable
fun getDropdownWidth(): List<Dp> {
    val textMeasurer = rememberTextMeasurer()
    val widthList = mutableListOf<Dp>()
    listOf(lengthUnits, weightUnits, timeUnits, temperatureUnits, surfaceUnits, volumeUnits, speedUnits).forEach { ref ->
        val allStrings = ref.map { "${stringResource(it.name)} (${it.abbreviation})" }
        with(LocalDensity.current) {
            widthList.add(allStrings.maxOf { textMeasurer.measure(it).size.width }.toDp())
        }
    }
    return widthList
}