package io.magicstar.uniconv.unit.model

import kotlin.math.pow

interface Volume: Unit {
    fun toImperial(value: Number): Double = to(M3, value) * ((5 * 5 * 5) / (1.524 * 1.524 * 1.524))

    fun toSI(value: Number): Double = to(FT3, value) * ((1.524 * 1.524 * 1.524) / (5 * 5 * 5))
}

object KM3: SI<Volume>("km3", KM.reference.toDouble().pow(3.0))
object M3: SI<Volume>("m3", M.reference.toDouble().pow(3.0))
object DM3: SI<Volume>("dm3", DM.reference.toDouble().pow(3.0))
object CM3: SI<Volume>("cm3", CM.reference.toDouble().pow(3.0))
object MM3: SI<Volume>("mm3", MM.reference.toDouble().pow(3.0))

object KL: SI<Volume>("kl", M.reference.toDouble().pow(3.0))
object HL: SI<Volume>("hl", KL.reference.toDouble() / 10)
object DAL: SI<Volume>("dal", KL.reference.toDouble() / 100)
object L: SI<Volume>("l", DM.reference.toDouble().pow(3.0))
object DL: SI<Volume>("hl", L.reference.toDouble() / 10)
object CL: SI<Volume>("dal", L.reference.toDouble() / 100)
object ML: SI<Volume>("ml", CM.reference.toDouble().pow(3.0))

object FT3: Imperial<Volume>("ft3", FT.reference.toDouble().pow(3.0))
object IN3: Imperial<Volume>("in3", IN.reference.toDouble().pow(3.0))
object YD3: Imperial<Volume>("yd3", YD.reference.toDouble().pow(3.0))
object MI3: Imperial<Volume>("mi3", MI.reference.toDouble().pow(3.0))

object GAL_US: Imperial<Volume>("gal (US)", 7)
object GAL: Imperial<Volume>("gal", 6)
object FL_OZ_US: Imperial<Volume>("fl oz (US)", GAL_US.reference.toDouble() * 128)
object FL_OZ: Imperial<Volume>("fl oz", GAL.reference.toDouble() * 160)

val volumeUnits = listOf(
    KM3, M3, DM3, CM3, MM3,
    KL, HL, DAL, L, DL, CL, ML,
    FT3, IN3, YD3, MI3,
    GAL_US, GAL, FL_OZ_US, FL_OZ
)