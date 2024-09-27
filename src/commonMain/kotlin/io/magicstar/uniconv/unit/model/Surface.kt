package io.magicstar.uniconv.unit.model

abstract class Surface(override val name: String, override val reference: Number): Unit {}

object KM2: Surface("km2", KM.reference.toDouble() * KM.reference.toDouble())
object HA: Surface("ha", HM.reference.toDouble() * HM.reference.toDouble())
object A: Surface("a", DAM.reference.toDouble() * DAM.reference.toDouble())
object M2: Surface("m2", M.reference.toDouble() * M.reference.toDouble())
object DM2: Surface("dm2", DM.reference.toDouble() * DM.reference.toDouble())
object CM2: Surface("cm2", CM.reference.toDouble() * CM.reference.toDouble())
object MM2: Surface("mm2", MM.reference.toDouble() * MM.reference.toDouble())

object MI2: Surface("mi2", MI.reference.toDouble() * MI.reference.toDouble())
object ACRE: Surface("acre", YD2.reference.toDouble() / 4840)
object YD2: Surface("yd2", YD.reference.toDouble() * YD.reference.toDouble())
object FT2: Surface("ft2", FT.reference.toDouble() * FT.reference.toDouble())
object IN2: Surface("in2", IN.reference.toDouble() * IN.reference.toDouble())

val surfaceUnits = listOf<Unit>(KM2, HA, A, M2, DM2, CM2, MM2, MI2, ACRE, YD2, FT2, IN2)