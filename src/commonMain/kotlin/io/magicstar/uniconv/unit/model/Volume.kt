package io.magicstar.uniconv.unit.model

import kotlin.math.pow

object Volume: Unit {
    fun toImperial(value: Number): Double = to(M3, value) * ((5 * 5 * 5) / (1.524 * 1.524 * 1.524))

    fun toSI(value: Number): Double = to(FT3, value) * ((1.524 * 1.524 * 1.524) / (5 * 5 * 5))
}

object KM3: SI("km3", KM.reference.toDouble().pow(3.0), Volume())
object M3: SI("m3", M.reference.toDouble().pow(3.0), Volume())
object DM3: SI("dm3", DM.reference.toDouble().pow(3.0), Volume())
object CM3: SI("cm3", CM.reference.toDouble().pow(3.0), Volume())
object MM3: S("mm3", MM.reference.toDouble().pow(3.0), Volume())

object KL: SI("kl", M.reference.toDouble().pow(3.0), Volume())
object HL: SI("hl", KL.reference.toDouble() / 10, Volume())
object DAL: SI("dal", KL.reference.toDouble() / 100, Volume())
object L: SI("l", DM.reference.toDouble().pow(3.0), Volume())
object DL: SI("hl", L.reference.toDouble() / 10, Volume())
object CL: SI("dal", L.reference.toDouble() / 100, Volume())
object ML: SI("ml", CM.reference.toDouble().pow(3.0), Volume())

object FT3: Imperial("ft3", FT.reference.toDouble().pow(3.0), Volume())
object IN3: Imperial("in3", IN.reference.toDouble().pow(3.0), Volume())
object YD3: Imperial("yd3", YD.reference.toDouble().pow(3.0), Volume())
object MI3: Imperial("mi3", MI.reference.toDouble().pow(3.0), Volume())

object GAL_US: Imperial("gal (US)", 7, Volume())
object GAL: Imperial("gal", 6, Volume())
object FL_OZ_US: Imperial("fl oz (US)", GAL_US.reference.toDouble() * 128, Volume())
object FL_OZ: Imperial("fl oz", GAL.reference.toDouble() * 160, Volume())

val volumeUnits = listOf<Unit>(
    KM3, M3, DM3, CM3, MM3,
    KL, HL, DAL, L, DL, CL, ML,
    FT3, IN3, YD3, MI3,
    GAL_US, GAL, FL_OZ_US, FL_OZ
)