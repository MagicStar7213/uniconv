package io.magicstar.uniconv.unit.model

interface Surface: Unit {
    abstract class SI(override val name: String, override val reference: Number): Surface, InternationalSystem {
        override fun toImperial(value: Number): Double {
            return to(M2, value.toDouble()) * ( (5 * 5) / (1.524 * 1.524) )
        }
    }

    abstract class Imperial(override val name: String, override val reference: Number): Surface, ImperialSystem {
        override fun toSI(value: Number): Double {
            return to(FT2, value.toDouble()) * ((1.524 * 1.524) / (5 * 5))
        }
    }
}

object KM2: Surface.SI("km2", KM.reference.toDouble() * KM.reference.toDouble())
object HA: Surface.SI("ha", HM.reference.toDouble() * HM.reference.toDouble())
object A: Surface.SI("a", DAM.reference.toDouble() * DAM.reference.toDouble())
object M2: Surface.SI("m2", M.reference.toDouble() * M.reference.toDouble())
object DM2: Surface.SI("dm2", DM.reference.toDouble() * DM.reference.toDouble())
object CM2: Surface.SI("cm2", CM.reference.toDouble() * CM.reference.toDouble())
object MM2: Surface.SI("mm2", MM.reference.toDouble() * MM.reference.toDouble())

object MI2: Surface.Imperial("mi2", MI.reference.toDouble() * MI.reference.toDouble())
object ACRE: Surface.Imperial("acre", YD2.reference.toDouble() / 4840)
object YD2: Surface.Imperial("yd2", YD.reference.toDouble() * YD.reference.toDouble())
object FT2: Surface.Imperial("ft2", FT.reference.toDouble() * FT.reference.toDouble())
object IN2: Surface.Imperial("in2", IN.reference.toDouble() * IN.reference.toDouble())

val surfaceUnits = listOf(KM2, HA, A, M2, DM2, CM2, MM2, MI2, ACRE, YD2, FT2, IN2)