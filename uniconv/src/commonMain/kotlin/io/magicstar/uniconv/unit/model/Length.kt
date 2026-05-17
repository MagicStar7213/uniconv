package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.cm
import io.magicstar.uniconv.generated.resources.dam
import io.magicstar.uniconv.generated.resources.dm
import io.magicstar.uniconv.generated.resources.ft
import io.magicstar.uniconv.generated.resources.hm
import io.magicstar.uniconv.generated.resources.`in`
import io.magicstar.uniconv.generated.resources.km
import io.magicstar.uniconv.generated.resources.length
import io.magicstar.uniconv.generated.resources.m
import io.magicstar.uniconv.generated.resources.mi
import io.magicstar.uniconv.generated.resources.mm
import io.magicstar.uniconv.generated.resources.yd

sealed class Length: Magnitude(Res.string.length) {

    data object MM : Unit(Res.string.mm, "mm", .001)
    data object CM : Unit(Res.string.cm, "cm", .01)
    data object DM : Unit(Res.string.dm, "dm", .1)
    data object M : Unit(Res.string.m, "m", 1)
    data object DAM : Unit(Res.string.dam, "dam", 10)
    data object HM : Unit(Res.string.hm, "hm", 100)
    data object KM : Unit(Res.string.km, "km", 1000)

    data object IN : Unit(Res.string.`in`, "in", FT.reference.toDouble() / 12)
    data object YD : Unit(Res.string.yd, "yd", 3 * FT.reference.toDouble())
    data object FT : Unit(Res.string.ft, "ft", 1 / 3.280839895)
    data object MI : Unit(Res.string.mi, "mi", 5280 * FT.reference.toDouble())
}
