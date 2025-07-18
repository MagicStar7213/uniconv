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
import org.jetbrains.compose.resources.StringResource

abstract class Weight(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object TON: Weight(Res.string.ton,"ton", 1_000_000)
object KG: Weight(Res.string.kg,"kg", 1000)
object HG: Weight(Res.string.hg,"hg", 100)
object DAG: Weight(Res.string.dag,"dag", 10)
object G: Weight(Res.string.g,"g", 1)
object DG: Weight(Res.string.dg,"dg", .1)
object CG: Weight(Res.string.cg,"cg", .01)
object MG: Weight(Res.string.mg,"mg", .001)

object OZ: Weight(Res.string.oz,"oz", 28.3495)
object LB: Weight(Res.string.lb,"lb", OZ.reference.toDouble() * 16)

val weightUnits = listOf<Unit>(TON, KG, HG, DAG, G, DG, CG, MG, OZ, LB)