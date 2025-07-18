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
import io.magicstar.uniconv.generated.resources.yd2
import org.jetbrains.compose.resources.StringResource

abstract class Surface(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object KM2: Surface(Res.string.km2,"km2", KM.reference.toDouble() * KM.reference.toDouble())
object HA: Surface(Res.string.ha,"ha", HM.reference.toDouble() * HM.reference.toDouble())
object A: Surface(Res.string.a,"a", DAM.reference.toDouble() * DAM.reference.toDouble())
object M2: Surface(Res.string.m2,"m2", M.reference.toDouble() * M.reference.toDouble())
object DM2: Surface(Res.string.dm2,"dm2", DM.reference.toDouble() * DM.reference.toDouble())
object CM2: Surface(Res.string.cm2,"cm2", CM.reference.toDouble() * CM.reference.toDouble())
object MM2: Surface(Res.string.mm2,"mm2", MM.reference.toDouble() * MM.reference.toDouble())

object MI2: Surface(Res.string.mi2,"mi2", MI.reference.toDouble() * MI.reference.toDouble())
object ACRE: Surface(Res.string.acre,"acre", YD2.reference.toDouble() * 4840)
object YD2: Surface(Res.string.yd2,"yd2", YD.reference.toDouble() * YD.reference.toDouble())
object FT2: Surface(Res.string.ft2,"ft2", FT.reference.toDouble() * FT.reference.toDouble())
object IN2: Surface(Res.string.in2,"in2", IN.reference.toDouble() * IN.reference.toDouble())

val surfaceUnits = listOf<Unit>(KM2, HA, A, M2, DM2, CM2, MM2, MI2, ACRE, YD2, FT2, IN2)