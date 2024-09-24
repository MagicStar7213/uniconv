package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }

    fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )
    fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
}

open class SI(override val name: String, override val reference: Number, val unit: Unit): Unit {
    override fun toImperial(value: Number): Double = unit.toImperial(value) 
}

open class Imperial(override val name: String, override val reference: Number, val unit: Unit): Unit {
    override fun toSI(value: Number): Double = unit.toSI(value)
}