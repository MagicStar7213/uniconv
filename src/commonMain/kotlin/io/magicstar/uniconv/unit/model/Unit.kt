package io.magicstar.uniconv.unit.model

import org.jetbrains.compose.resources.StringResource

interface Unit {
    val name: StringResource
    val abbreviation: String
    val reference: Number

    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}