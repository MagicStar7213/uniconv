package io.magicstar.uniconv.unit.model

interface Speed: Unit {
    fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )

    fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
}

object KM_H: SI<Speed>("km/h", KM.reference.toDouble() / H.reference.toDouble())
object M_S: SI<Speed>("m/s", M.reference.toDouble() / S.reference.toDouble())
object MI_H: Imperial<Speed>("mi/h", MI.reference.toDouble() / H.reference.toDouble())
object KN: SI<Speed>("kn", KM_H.reference.toDouble() * 1.852)
object FT_S: Imperial<Speed>("ft/s", FT.reference.toDouble() / S.reference.toDouble())

val speedUnits = listOf(KM_H, M_S, MI_H, KN, FT_S)