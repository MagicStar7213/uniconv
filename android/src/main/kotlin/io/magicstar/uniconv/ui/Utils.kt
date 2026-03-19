package io.magicstar.uniconv.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import java.text.Normalizer

@Composable
fun GetDropdownWidth(
    view: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", view)[0]
            .measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}

fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}