package io.magicstar.uniconv.unit.model

abstract class Weight(override val name: String, override val reference: Number): Unit {}

object TON: Weight("ton", 1_000_000)
object KG: Weight("kg", 1000)
object HG: Weight("hg", 100)
object DAG: Weight("dag", 10)
object G: Weight("g", 1)
object DG: Weight("dg", .1)
object CG: Weight("cg", .01)
object MG: Weight("mg", .001)

object OZ: Weight("oz", 0.035274)
object LB: Weight("lb", OZ.reference.toDouble() * 16)

val weightUnits = listOf<Unit>(TON, KG, HG, DAG, G, DG, CG, MG, OZ, LB)