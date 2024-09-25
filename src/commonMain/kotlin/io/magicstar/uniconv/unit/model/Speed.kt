package io.magicstar.uniconv.unit.model

abstract class Speed: Unit {
    open class SI(override val name: String, override val reference: Number): InternationalSys {
        override fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )
    }

    open class Imperial(override val name: String, override val reference: Number): ImperialSys {
        override fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
    }
}

object KM_H: Speed.SI("km/h", KM.reference.toDouble() / H.reference.toDouble())
object M_S: Speed.SI("m/s", M.reference.toDouble() / S.reference.toDouble())
object MI_H: Speed.Imperial("mi/h", MI.reference.toDouble() / H.reference.toDouble())
object KN: Speed.SI("kn", KM_H.reference.toDouble() * 1.852)
object FT_S: Speed.Imperial("ft/s", FT.reference.toDouble() / S.reference.toDouble())

val speedUnits = listOf<Unit>(KM_H, M_S, MI_H, KN, FT_S)