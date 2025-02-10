package io.magicstar.uniconv.unit.model

abstract class Speed(override val name: String, override val reference: Number): Unit

object KM_H: Speed("km/h", KM.reference.toDouble() / H.reference.toDouble())
object M_S: Speed("m/s", M.reference.toDouble() / S.reference.toDouble())
object MI_H: Speed("mi/h", MI.reference.toDouble() / H.reference.toDouble())
object KN: Speed("kn", (1.852 * KM.reference.toDouble()) / H.reference.toDouble())
object FT_S: Speed("ft/s", FT.reference.toDouble() / S.reference.toDouble())

val speedUnits = listOf<Unit>(KM_H, M_S, MI_H, KN, FT_S)