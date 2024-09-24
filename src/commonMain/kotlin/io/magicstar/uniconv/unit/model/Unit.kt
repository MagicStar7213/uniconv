package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

open class SI(override val name: String, override val reference: Number, val unit: Unit): Unit {
    fun toImperial(value: Number): Double = unit.toImperial(value) 
}

open class Imperial(override val name: String, override val reference: Number, val unit: Unit): Unit {
    fun toSI(value: Number): Double = unit.toSI(value)
}