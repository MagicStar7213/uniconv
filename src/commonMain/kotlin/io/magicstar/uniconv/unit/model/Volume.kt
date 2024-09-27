package io.magicstar.uniconv.unit.model

import kotlin.math.pow

class Volume(override val name: String, override val reference: Number): Unit {}

object KM3: Volume("km3", KM.reference.toDouble().pow(3.0))
object M3: Volume("m3", M.reference.toDouble().pow(3.0))
object DM3: Volume("dm3", DM.reference.toDouble().pow(3.0))
object CM3: Volume("cm3", CM.reference.toDouble().pow(3.0))
object MM3: Volume("mm3", MM.reference.toDouble().pow(3.0))

object KL: Volume("kl", M.reference.toDouble().pow(3.0))
object HL: Volume("hl", KL.reference.toDouble() / 10)
object DAL: Volume("dal", KL.reference.toDouble() / 100)
object L: Volume("l", DM.reference.toDouble().pow(3.0))
object DL: Volume("hl", L.reference.toDouble() / 10)
object CL: Volume("dal", L.reference.toDouble() / 100)
object ML: Volume("ml", CM.reference.toDouble().pow(3.0))

object FT3: Volume("ft3", FT.reference.toDouble().pow(3.0))
object IN3: Volume("in3", IN.reference.toDouble().pow(3.0))
object YD3: Volume("yd3", YD.reference.toDouble().pow(3.0))
object MI3: Volume("mi3", MI.reference.toDouble().pow(3.0))

object GAL_US: Volume("gal (US)", 7)
object GAL: Volume("gal", 6)
object FL_OZ_US: Volume("fl oz (US)", GAL_US.reference.toDouble() * 128)
object FL_OZ: Volume("fl oz", GAL.reference.toDouble() * 160)

val volumeUnits = listOf<Unit>(
    KM3, M3, DM3, CM3, MM3,
    KL, HL, DAL, L, DL, CL, ML,
    FT3, IN3, YD3, MI3,
    GAL_US, GAL, FL_OZ_US, FL_OZ
)