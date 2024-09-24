package io.magicstar.uniconv.unit.model

interface Weight: Unit {
    fun toImperial(value: Number): Double = to(G, value.toDouble()) * (385.81 / 25)

    fun toSI(value: Number): Double = to(GRAINS, value.toDouble()) * (25 / 385.81)
}

object TON: SI<Weight>("ton", 1_000_000)
object KG: SI<Weight>("kg", 1000)
object HG: SI<Weight>("hg", 100)
object DAG: SI<Weight>("dag", 10)
object G: SI<Weight>("g", 1)
object DG: SI<Weight>("dg", .1)
object CG: SI<Weight>("cg", .01)
object MG: SI<Weight>("mg", .001)

object GRAINS: Imperial<Weight>("gr", .08 / 35)
object OZ: Imperial<Weight>("oz", 1)
object LB: Imperial<Weight>("lb", 16)

val weightUnits = listOf(TON, KG, HG, DAG, G, DG, CG, MG, GRAINS, OZ, LB)