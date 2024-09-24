package io.magicstar.uniconv.unit.model

class Length: Unit {
    override val name: String = ""
    override val reference: Number = 0

    override fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )
    override fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
}

object MM: SI("mm", .001, Length())
object CM: SI("cm", .01, Length())
object DM: SI("dm", .1, Length())
object M: SI("m", 1, Length())
object DAM: SI("dam", 10, Length())
object HM: SI("hm", 100, Length())
object KM: SI("km", 1000, Length())

object IN: Imperial("in", 1/12, Length())
object YD: Imperial("yd", 3, Length())
object FT: Imperial("ft", 1, Length())
object MI: Imperial("mi", 5280, Length())

val lengthUnits = listOf<Unit>(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)