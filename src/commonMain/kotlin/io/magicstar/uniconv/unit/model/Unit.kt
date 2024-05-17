package io.magicstar.uniconv.unit.model

interface Unit {
    val name: String
    val reference: Number
    fun to(unit: Unit, value: Number): Double {
        return value.toDouble() * (reference.toDouble() / unit.reference.toDouble())
    }
}

interface InternationalSystem {
    fun toImperial(value: Number): Double {
        return value.toDouble() * ( 5 / 1.524 )
    }
}
interface ImperialSystem {
    fun toSI(value: Number): Double {
        return value.toDouble() * (1.524 / 5)
    }
}