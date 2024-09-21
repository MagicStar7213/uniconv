package io.magicstar.uniconv.unit.model

import kotlin.math.pow

interface Volume: Unit {
    abstract class SI(override val name: String, override val reference: Number): Volume, InternationalSystem {
        override fun toImperial(value: Number): Double {
            return to(FT3, value) * ((5 * 5 * 5) / (1.524 * 1.524 * 1.524))
        }
    }

    abstract class Imperial(override val name: String, override val reference: Number): Volume, ImperialSystem {
        override fun toSI(value: Number): Double {
            return to(M3, value) * ((1.524 * 1.524 * 1.524) / (5 * 5 * 5))
        }
    }
}

object KM3: Volume.SI("km3", KM.reference.toDouble().pow(3.0))
object M3: Volume.SI("m3", M.reference.toDouble().pow(3.0))
object DM3: Volume.SI("dm3", DM.reference.toDouble().pow(3.0))
object CM3: Volume.SI("cm3", CM.reference.toDouble().pow(3.0))
object MM3: Volume.SI("mm3", MM.reference.toDouble().pow(3.0))

object KL: Volume.SI("kl", M.reference.toDouble().pow(3.0))
object HL: Volume.SI("hl", KL.reference.toDouble() / 10)
object DAL: Volume.SI("dal", KL.reference.toDouble() / 100)
object L: Volume.SI("l", DM.reference.toDouble().pow(3.0))
object DL: Volume.SI("hl", L.reference.toDouble() / 10)
object CL: Volume.SI("dal", L.reference.toDouble() / 100)
object ML: Volume.SI("ml", CM.reference.toDouble().pow(3.0))

object FT3: Volume.Imperial("ft3", FT.reference.toDouble().pow(3.0))
object IN3: Volume.Imperial("in3", IN.reference.toDouble().pow(3.0))
object YD3: Volume.Imperial("yd3", YD.reference.toDouble().pow(3.0))
object MI3: Volume.Imperial("mi3", MI.reference.toDouble().pow(3.0))

object GAL_US: Volume.Imperial("gal (US)", 7)
object GAL: Volume.Imperial("gal", 6)
object FL_OZ_US: Volume.Imperial("fl oz (US)", GAL_US.reference.toDouble() * 128)
object FL_OZ: Volume.Imperial("fl oz", GAL.reference.toDouble() * 160)

val volumeUnits = listOf(
    KM3, M3, DM3, CM3, MM3,
    KL, HL, DAL, L, DL, CL, ML,
    FT3, IN3, YD3, MI3,
    GAL_US, GAL, FL_OZ_US, FL_OZ
)