package io.magicstar.uniconv.unit.model

class Speed: Unit {
    override val name: String = ""
    override val reference: Number = 0
    
    override fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )
    override fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
}

object KM_H: SI("km/h", KM.reference.toDouble() / H.reference.toDouble(), Speed())
object M_S: SI("m/s", M.reference.toDouble() / S.reference.toDouble(), Speed())
object MI_H: Imperial("mi/h", MI.reference.toDouble() / H.reference.toDouble(), Speed())
object KN: SI("kn", KM_H.reference.toDouble() * 1.852, Speed())
object FT_S: Imperial("ft/s", FT.reference.toDouble() / S.reference.toDouble(), Speed())

val speedUnits = listOf<Unit>(KM_H, M_S, MI_H, KN, FT_S)