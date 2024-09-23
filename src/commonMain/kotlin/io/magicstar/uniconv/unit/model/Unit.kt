package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: T, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

abstract class SI<T>(override val name: String, override val reference: Number): Unit {
    fun toImperial(value: Number): Double = T.toImperial(value)
}
abstract class Imperial<T>(override val name: String, override val reference: Number): Unit {
    fun toSI(value: Number): Double = T.toSI(value)
}