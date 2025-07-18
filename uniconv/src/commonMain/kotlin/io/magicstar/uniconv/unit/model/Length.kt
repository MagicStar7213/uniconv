package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.cm
import io.magicstar.uniconv.generated.resources.dam
import io.magicstar.uniconv.generated.resources.dm
import io.magicstar.uniconv.generated.resources.ft
import io.magicstar.uniconv.generated.resources.hm
import io.magicstar.uniconv.generated.resources.`in`
import io.magicstar.uniconv.generated.resources.km
import io.magicstar.uniconv.generated.resources.m
import io.magicstar.uniconv.generated.resources.mi
import io.magicstar.uniconv.generated.resources.mm
import io.magicstar.uniconv.generated.resources.yd
import org.jetbrains.compose.resources.StringResource

abstract class Length(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object MM: Length(Res.string.mm, "mm", .001)
object CM: Length(Res.string.cm,"cm", .01)
object DM: Length(Res.string.dm,"dm", .1)
object M: Length(Res.string.m,"m", 1)
object DAM: Length(Res.string.dam,"dam", 10)
object HM: Length(Res.string.hm,"hm", 100)
object KM: Length(Res.string.km,"km", 1000)

object IN: Length(Res.string.`in`,"in", FT.reference.toDouble() / 12)
object YD: Length(Res.string.yd,"yd", 3 * FT.reference.toDouble())
object FT: Length(Res.string.ft,"ft", 1/3.280839895)
object MI: Length(Res.string.mi,"mi", 5280 * FT.reference.toDouble())

val lengthUnits = listOf<Unit>(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)
