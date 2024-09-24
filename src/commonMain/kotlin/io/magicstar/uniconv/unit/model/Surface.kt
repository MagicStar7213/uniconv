package io.magicstar.uniconv.unit.model

class Surface: Unit {
    override val name: String = ""
    override val reference: Number = 0
    
    override fun toImperial(value: Number): Double = to(M2, value.toDouble()) * ( (5 * 5) / (1.524 * 1.524) )
    override fun toSI(value: Number): Double = to(FT2, value.toDouble()) * ((1.524 * 1.524) / (5 * 5))
}

object KM2: SI("km2", KM.reference.toDouble() * KM.reference.toDouble(), Surface())
object HA: SI("ha", HM.reference.toDouble() * HM.reference.toDouble(), Surface())
object A: SI("a", DAM.reference.toDouble() * DAM.reference.toDouble(), Surface())
object M2: SI("m2", M.reference.toDouble() * M.reference.toDouble(), Surface())
object DM2: SI("dm2", DM.reference.toDouble() * DM.reference.toDouble(), Surface())
object CM2: SI("cm2", CM.reference.toDouble() * CM.reference.toDouble(), Surface())
object MM2: SI("mm2", MM.reference.toDouble() * MM.reference.toDouble(), Surface())

object MI2: Imperial("mi2", MI.reference.toDouble() * MI.reference.toDouble(), Surface())
object ACRE: Imperial("acre", YD2.reference.toDouble() / 4840, Surface())
object YD2: Imperial("yd2", YD.reference.toDouble() * YD.reference.toDouble(), Surface())
object FT2: Imperial("ft2", FT.reference.toDouble() * FT.reference.toDouble(), Surface())
object IN2: Imperial("in2", IN.reference.toDouble() * IN.reference.toDouble(), Surface())

val surfaceUnits = listOf<Unit>(KM2, HA, A, M2, DM2, CM2, MM2, MI2, ACRE, YD2, FT2, IN2)