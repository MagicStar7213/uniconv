package io.magicstar.uniconv.unit.model

abstract class Time(override val name: String, override val reference: Number): Unit

object MS: Time("ms", .001)
object S: Time("s", 1)
object MIN: Time("min", 60)
object H: Time("h", 3600)
object DAY: Time("day", H.reference.toDouble() * 24)
object WEEK: Time("week", DAY.reference.toDouble() * 7)
object MONTH: Time("month", DAY.reference.toDouble() * 30)
object YEAR: Time("year", DAY.reference.toDouble() * 365)
object DECADE: Time("decade", YEAR.reference.toDouble() * 10)
object CENTURY: Time("century", YEAR.reference.toDouble() * 100)
object MILLENNIUM: Time("millenium", YEAR.reference.toDouble() * 1000)

val timeUnits = listOf(MS, S, MIN, H, DAY, WEEK, MONTH, YEAR, DECADE, CENTURY, MILLENNIUM)