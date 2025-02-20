package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.ft_s
import io.magicstar.uniconv.generated.resources.km_h
import io.magicstar.uniconv.generated.resources.kn
import io.magicstar.uniconv.generated.resources.m_s
import io.magicstar.uniconv.generated.resources.mi_h
import org.jetbrains.compose.resources.StringResource

abstract class Speed(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object KM_H: Speed(Res.string.km_h,"km/h", KM.reference.toDouble() / H.reference.toDouble())
object M_S: Speed(Res.string.m_s,"m/s", M.reference.toDouble() / S.reference.toDouble())
object MI_H: Speed(Res.string.mi_h,"mi/h", MI.reference.toDouble() / H.reference.toDouble())
object KN: Speed(Res.string.kn,"kn", (1.852 * KM.reference.toDouble()) / H.reference.toDouble())
object FT_S: Speed(Res.string.ft_s,"ft/s", FT.reference.toDouble() / S.reference.toDouble())

val speedUnits = listOf<Unit>(KM_H, M_S, MI_H, KN, FT_S)