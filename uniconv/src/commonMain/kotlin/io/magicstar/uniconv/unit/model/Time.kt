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
import io.magicstar.uniconv.generated.resources.time
import io.magicstar.uniconv.generated.resources.week
import io.magicstar.uniconv.generated.resources.year

class Time: Magnitude("tm",Res.string.time) {

    class MS: Unit(Res.string.ms,"ms", .001)
    class S: Unit(Res.string.s,"s", 1)
    class MIN: Unit(Res.string.min,"min", 60)
    class H: Unit(Res.string.h,"h", 3600)
    class DAY: Unit(Res.string.day,"day", H().reference.toDouble() * 24)
    class WEEK: Unit(Res.string.week,"week", DAY().reference.toDouble() * 7)
    class MONTH: Unit(Res.string.month,"month", DAY().reference.toDouble() * 30)
    class YEAR: Unit(Res.string.year,"year", DAY().reference.toDouble() * 365)
    class DECADE: Unit(Res.string.decade,"decade", YEAR().reference.toDouble() * 10)
    class CENTURY: Unit(Res.string.century,"century", YEAR().reference.toDouble() * 100)
    class MILLENNIUM: Unit(Res.string.millennium,"millennium", YEAR().reference.toDouble() * 1000)
}