package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

interface InternationalSys: Unit {
    fun toImperial(value: Number): Double
}

interface ImperialSys: Unit {
    fun toSI(value: Number): Double
}