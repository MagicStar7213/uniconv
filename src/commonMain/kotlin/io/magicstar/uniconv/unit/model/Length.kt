package io.magicstar.uniconv.unit.model

abstract class Length(override val name: String, override val reference: Number): Unit {}

object MM: Length("mm", .001)
object CM: Length("cm", .01)
object DM: Length("dm", .1)
object M: Length("m", 1)
object DAM: Length("dam", 10)
object HM: Length("hm", 100)
object KM: Length("km", 1000)

object IN: Length("in", (1/12) * FT.reference.toDouble())
object YD: Length("yd", 3 * FT.reference.toDouble())
object FT: Length("ft", 1/3.28084)
object MI: Length("mi", 5280 * FT.reference.toDouble())

val lengthUnits = listOf<Unit>(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)
