package io.magicstar.uniconv.unit.model

interface Speed: Unit {
    abstract class SI(override val name: String, override val reference: Number): Speed, InternationalSystem {
        override fun toImperial(value: Number): Double {
            return super.toImperial(to(FT, value))
        }
    }

    abstract class Imperial(override val name: String, override val reference: Number): Speed, ImperialSystem {
        override fun toSI(value: Number): Double {
            return super.toSI(to(M, value))
        }
    }
}

object KM_H: Speed.SI("km/h", KM.reference.toDouble() / H.reference.toDouble())
object M_S: Speed.SI("m/s", M.reference.toDouble() / S.reference.toDouble())
object MI_H: Speed.Imperial("mi/h", MI.reference.toDouble() / H.reference.toDouble())
object KN: Speed.SI("kn", KM_H.reference.toDouble() * 1.852)
object FT_S: Speed.Imperial("ft/s", FT.reference.toDouble() / S.reference.toDouble())

val speedUnits = listOf(KM_H, M_S, MI_H, KN, FT_S)