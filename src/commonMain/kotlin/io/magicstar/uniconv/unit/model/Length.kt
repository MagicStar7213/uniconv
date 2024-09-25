package io.magicstar.uniconv.unit.model

abstract class Length: Unit {
    open class SI(override val name: String, override val reference: Number): InternationalSys {
        override fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )
    }
    
    open class Imperial(override val name: String, override val reference: Number): ImperialSys {
        override fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
    }
}

object MM: Length.SI("mm", .001)
object CM: Length.SI("cm", .01)
object DM: Length.SI("dm", .1)
object M: Length.SI("m", 1)
object DAM: Length.SI("dam", 10)
object HM: Length.SI("hm", 100)
object KM: Length.SI("km", 1000)

object IN: Length.Imperial("in", (1/12))
object YD: Length.Imperial("yd", 3)
object FT: Length.Imperial("ft", 1)
object MI: Length.Imperial("mi", 5280)

val lengthUnits = listOf<Unit>(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)