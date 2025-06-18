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
fun getDropdownWidth(): Dp {
    with(LocalDensity.current) {
        val textMeasurer = rememberTextMeasurer()
        val widthList = mutableListOf<Dp>()
        for (ref in listOf(lengthUnits, weightUnits, timeUnits, temperatureUnits, surfaceUnits, volumeUnits, speedUnits)) {
            val allStrings = ref.map { "${stringResource(it.name)} (${it.abbreviation})" }
            widthList.add(allStrings.maxOf { textMeasurer.measure(it).size.width }.toDp())
        }
        return widthList.maxBy { it.toPx() }
    }
}