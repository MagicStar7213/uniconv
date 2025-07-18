package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.century
import io.magicstar.uniconv.generated.resources.day
import io.magicstar.uniconv.generated.resources.decade
import io.magicstar.uniconv.generated.resources.h
import io.magicstar.uniconv.generated.resources.millennium
import io.magicstar.uniconv.generated.resources.min
import io.magicstar.uniconv.generated.resources.month
import io.magicstar.uniconv.generated.resources.ms
import io.magicstar.uniconv.generated.resources.s
import io.magicstar.uniconv.generated.resources.week
import io.magicstar.uniconv.generated.resources.year
import org.jetbrains.compose.resources.StringResource

abstract class Time(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object MS: Time(Res.string.ms,"ms", .001)
object S: Time(Res.string.s,"s", 1)
object MIN: Time(Res.string.min,"min", 60)
object H: Time(Res.string.h,"h", 3600)
object DAY: Time(Res.string.day,"day", H.reference.toDouble() * 24)
object WEEK: Time(Res.string.week,"week", DAY.reference.toDouble() * 7)
object MONTH: Time(Res.string.month,"month", DAY.reference.toDouble() * 30)
object YEAR: Time(Res.string.year,"year", DAY.reference.toDouble() * 365)
object DECADE: Time(Res.string.decade,"decade", YEAR.reference.toDouble() * 10)
object CENTURY: Time(Res.string.century,"century", YEAR.reference.toDouble() * 100)
object MILLENNIUM: Time(Res.string.millennium,"millennium", YEAR.reference.toDouble() * 1000)

val timeUnits = listOf(MS, S, MIN, H, DAY, WEEK, MONTH, YEAR, DECADE, CENTURY, MILLENNIUM)