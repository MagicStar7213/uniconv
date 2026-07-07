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

class Weight: Magnitude("wg", Res.string.weight) {

    class TON: Unit(Res.string.ton,"ton", 1_000_000)
    class KG: Unit(Res.string.kg,"kg", 1000)
    class HG: Unit(Res.string.hg,"hg", 100)
    class DAG: Unit(Res.string.dag,"dag", 10)
    class G: Unit(Res.string.g,"g", 1)
    class DG: Unit(Res.string.dg,"dg", .1)
    class CG: Unit(Res.string.cg,"cg", .01)
    class MG: Unit(Res.string.mg,"mg", .001)

    class OZ: Unit(Res.string.oz,"oz", 28.3495)
    class LB: Unit(Res.string.lb,"lb", OZ().reference.toDouble() * 16)
}