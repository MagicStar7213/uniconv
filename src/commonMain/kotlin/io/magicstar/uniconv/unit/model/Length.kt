package io.magicstar.uniconv.unit.model

class Length(override val name: String, override val reference: Number): Unit {}

object MM: Length("mm", .001)
object CM: Length("cm", .01)
object DM: Length("dm", .1)
object M: Length("m", 1)
object DAM: Length("dam", 10)
object HM: Length("hm", 100)
object KM: Length("km", 1000)

object IN: Length("in", (1/12)*(1.524/5))
object YD: Length("yd", 3*(1.524/5))
object FT: Length("ft", 1*(1.524/5))
object MI: Length("mi", 5280*(1.524/5))

val lengthUnits = listOf<Unit>(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)