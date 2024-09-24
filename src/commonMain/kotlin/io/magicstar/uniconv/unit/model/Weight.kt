package io.magicstar.uniconv.unit.model

class Weight: Unit {
    override val name: String = ""
    override val reference: Number = 0
    
    fun toImperial(value: Number): Double = to(G, value.toDouble()) * (385.81 / 25)

    fun toSI(value: Number): Double = to(GRAINS, value.toDouble()) * (25 / 385.81)
}

object TON: SI("ton", 1_000_000, Weight())
object KG: SI("kg", 1000, Weight())
object HG: SI("hg", 100, Weight())
object DAG: SI("dag", 10, Weight())
object G: SI("g", 1, Weight())
object DG: SI("dg", .1, Weight())
object CG: SI("cg", .01, Weight())
object MG: SI("mg", .001, Weight())

object GRAINS: Imperial("gr", .08 / 35, Weight())
object OZ: Imperial("oz", 1, Weight())
object LB: Imperial("lb", 16, Weight())

val weightUnits = listOf<Unit>(TON, KG, HG, DAG, G, DG, CG, MG, GRAINS, OZ, LB)