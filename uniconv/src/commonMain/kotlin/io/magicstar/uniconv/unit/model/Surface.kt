package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.a
import io.magicstar.uniconv.generated.resources.acre
import io.magicstar.uniconv.generated.resources.cm2
import io.magicstar.uniconv.generated.resources.dm2
import io.magicstar.uniconv.generated.resources.ft2
import io.magicstar.uniconv.generated.resources.ha
import io.magicstar.uniconv.generated.resources.in2
import io.magicstar.uniconv.generated.resources.km2
import io.magicstar.uniconv.generated.resources.m2
import io.magicstar.uniconv.generated.resources.mi2
import io.magicstar.uniconv.generated.resources.mm2
import io.magicstar.uniconv.generated.resources.surface
import io.magicstar.uniconv.generated.resources.yd2
import kotlin.math.pow

sealed class Surface: Magnitude(Res.string.surface) {

    data object KM2 : Unit(Res.string.km2, "km2", Length.KM.reference.toDouble().pow(2))
    data object HA : Unit(Res.string.ha, "ha", Length.HM.reference.toDouble().pow(2))
    data object A : Unit(Res.string.a, "a", Length.DAM.reference.toDouble().pow(2))
    data object M2 : Unit(Res.string.m2, "m2", Length.M.reference.toDouble().pow(2))
    data object DM2 : Unit(Res.string.dm2, "dm2", Length.DM.reference.toDouble().pow(2))
    data object CM2 : Unit(Res.string.cm2, "cm2", Length.CM.reference.toDouble().pow(2))
    data object MM2 : Unit(Res.string.mm2, "mm2", Length.MM.reference.toDouble().pow(2))

    data object MI2 : Unit(Res.string.mi2, "mi2", Length.MI.reference.toDouble().pow(2))
    data object ACRE : Unit(Res.string.acre, "acre", YD2.reference.toDouble() * 4840)
    data object YD2 : Unit(Res.string.yd2, "yd2", Length.YD.reference.toDouble().pow(2))
    data object FT2 : Unit(Res.string.ft2, "ft2", Length.FT.reference.toDouble().pow(2))
    data object IN2 : Unit(Res.string.in2, "in2", Length.IN.reference.toDouble().pow(2))
}