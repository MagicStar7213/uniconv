package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

open class SI<T: Unit>(override val name: String, override val reference: Number): Unit {
    fun toImperial(value: Number): Double = T.toImperial(value) 
}

open class Imperial<T: Unit>(override val name: String, override val reference: Number): Unit {
    fun toSI(value: Number): Double = T.toSI(value)
}