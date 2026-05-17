package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.cg
import io.magicstar.uniconv.generated.resources.dag
import io.magicstar.uniconv.generated.resources.dg
import io.magicstar.uniconv.generated.resources.g
import io.magicstar.uniconv.generated.resources.hg
import io.magicstar.uniconv.generated.resources.kg
import io.magicstar.uniconv.generated.resources.lb
import io.magicstar.uniconv.generated.resources.mg
import io.magicstar.uniconv.generated.resources.oz
import io.magicstar.uniconv.generated.resources.ton
import io.magicstar.uniconv.generated.resources.weight

sealed class Weight: Magnitude(Res.string.weight) {

    data object TON: Unit(Res.string.ton,"ton", 1_000_000)
    data object KG: Unit(Res.string.kg,"kg", 1000)
    data object HG: Unit(Res.string.hg,"hg", 100)
    data object DAG: Unit(Res.string.dag,"dag", 10)
    data object G: Unit(Res.string.g,"g", 1)
    data object DG: Unit(Res.string.dg,"dg", .1)
    data object CG: Unit(Res.string.cg,"cg", .01)
    data object MG: Unit(Res.string.mg,"mg", .001)

    data object OZ: Unit(Res.string.oz,"oz", 28.3495)
    data object LB: Unit(Res.string.lb,"lb", OZ.reference.toDouble() * 16)
}