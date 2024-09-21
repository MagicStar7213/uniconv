package io.magicstar.uniconv.unit.model

interface Length: Unit {
    abstract class SI(override val name: String, override val reference: Number): Length, InternationalSystem {
        override fun toImperial(value: Number): Double {
            return super.toImperial(to(FT, value))
        }
    }

    abstract class Imperial(override val name: String, override val reference: Number): Length, ImperialSystem {
        override fun toSI(value: Number): Double {
            return super.toSI(to(M, value))
        }
    }
}

object MM: Length.SI("mm", .001)
object CM: Length.SI("cm", .01)
object DM: Length.SI("dm", .1)
object M: Length.SI("m", 1)
object DAM: Length.SI("dam", 10)
object HM: Length.SI("hm", 100)
object KM: Length.SI("km", 1000)

object IN: Length.Imperial("in", 1/12)
object YD: Length.Imperial("yd", 3)
object FT: Length.Imperial("ft", 1)
object MI: Length.Imperial("mi", 5280)

val lengthUnits = listOf(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)