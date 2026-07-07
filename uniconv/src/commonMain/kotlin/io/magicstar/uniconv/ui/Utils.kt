package io.magicstar.uniconv.ui

import androidx.annotation.RequiresApi
import java.text.Normalizer

@RequiresApi(9)
fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}