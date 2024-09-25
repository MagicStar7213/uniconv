package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

interface InternationalSys(override val name: String, override val reference: Number, val unit: Unit): Unit {
    override fun toImperial(value: Number): Double = unit.toImperial(value) 
}

interface ImperialSys(override val name: String, override val reference: Number, val unit: Unit): Unit {
    override fun toSI(value: Number): Double = unit.toSI(value)
}