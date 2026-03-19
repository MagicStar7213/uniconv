package io.magicstar.uniconv.ui

import java.text.Normalizer

fun String.normalize(): String {
    val unaccentRegex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    return unaccentRegex.replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")
}