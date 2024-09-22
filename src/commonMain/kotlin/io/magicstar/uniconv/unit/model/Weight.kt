package io.magicstar.uniconv.unit.model

interface Weight: Unit {
    abstract class SI(override val name: String, override val reference: Number): Weight, InternationalSystem {
        override fun toImperial(value: Number): Double {
            return to(G, value.toDouble()) * (385.81 / 25)
        }
    }

    abstract class Imperial(override val name: String, override val reference: Number): Weight, ImperialSystem {
        override fun toSI(value: Number): Double {
            return to(GRAINS, value.toDouble()) * (25 / 385.81)
        }
    }
}

object TON: Weight.SI("ton", 1_000_000)
object KG: Weight.SI("kg", 1000)
object HG: Weight.SI("hg", 100)
object DAG: Weight.SI("dag", 10)
object G: Weight.SI("g", 1)
object DG: Weight.SI("dg", .1)
object CG: Weight.SI("cg", .01)
object MG: Weight.SI("mg", .001)

object GRAINS: Weight.Imperial("gr", .08 / 35)
object OZ: Weight.Imperial("oz", 1)
object LB: Weight.Imperial("lb", 16)

val weightUnits = listOf(TON, KG, HG, DAG, G, DG, CG, MG, GRAINS, OZ, LB)