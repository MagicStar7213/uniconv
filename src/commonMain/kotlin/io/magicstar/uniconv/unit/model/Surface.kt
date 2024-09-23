package io.magicstar.uniconv.unit.model

interface Surface {
    fun toImperial(value: Number): Double = to(M2, value.toDouble()) * ( (5 * 5) / (1.524 * 1.524) )

    fun toSI(value: Number): Double = to(FT2, value.toDouble()) * ((1.524 * 1.524) / (5 * 5))
}

object KM2: SI<Surface>("km2", KM.reference.toDouble() * KM.reference.toDouble())
object HA: SI<Surface>("ha", HM.reference.toDouble() * HM.reference.toDouble())
object A: SI<Surface>("a", DAM.reference.toDouble() * DAM.reference.toDouble())
object M2: SI<Surface>("m2", M.reference.toDouble() * M.reference.toDouble())
object DM2: SI<Surface>("dm2", DM.reference.toDouble() * DM.reference.toDouble())
object CM2: SI<Surface>("cm2", CM.reference.toDouble() * CM.reference.toDouble())
object MM2: SI<Surface>("mm2", MM.reference.toDouble() * MM.reference.toDouble())

object MI2: Imperial<Surface>("mi2", MI.reference.toDouble() * MI.reference.toDouble())
object ACRE: Imperial<Surface>("acre", YD2.reference.toDouble() / 4840)
object YD2: Imperial<Surface>("yd2", YD.reference.toDouble() * YD.reference.toDouble())
object FT2: Imperial<Surface>("ft2", FT.reference.toDouble() * FT.reference.toDouble())
object IN2: Imperial<Surface>("in2", IN.reference.toDouble() * IN.reference.toDouble())

val surfaceUnits = listOf(KM2, HA, A, M2, DM2, CM2, MM2, MI2, ACRE, YD2, FT2, IN2)