package io.magicstar.uniconv.unit.model

interface Length: Unit {
    fun toImperial(value: Number): Double = to(M, value).toDouble() * ( 5 / 1.524 )

    fun toSI(value: Number): Double = to(FT, value).toDouble() * (1.524 / 5)
}

object MM: SI<Length>("mm", .001)
object CM: SI<Length>("cm", .01)
object DM: SI<Length>("dm", .1)
object M: SI<Length>("m", 1)
object DAM: SI<Length>("dam", 10)
object HM: SI<Length>("hm", 100)
object KM: SI<Length>("km", 1000)

object IN: Imperial<Length>("in", 1/12)
object YD: Imperial<Length>("yd", 3)
object FT: Imperial<Length>("ft", 1)
object MI: Imperial<Length>("mi", 5280)

val lengthUnits = listOf(MM, CM, DM, M, DAM, HM, KM, IN, YD, FT, MI)