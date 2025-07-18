package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.cm3
import io.magicstar.uniconv.generated.resources.cup
import io.magicstar.uniconv.generated.resources.dal
import io.magicstar.uniconv.generated.resources.dm3
import io.magicstar.uniconv.generated.resources.fl_oz
import io.magicstar.uniconv.generated.resources.ft3
import io.magicstar.uniconv.generated.resources.gal
import io.magicstar.uniconv.generated.resources.hl
import io.magicstar.uniconv.generated.resources.in3
import io.magicstar.uniconv.generated.resources.kl
import io.magicstar.uniconv.generated.resources.km3
import io.magicstar.uniconv.generated.resources.l
import io.magicstar.uniconv.generated.resources.m3
import io.magicstar.uniconv.generated.resources.mi3
import io.magicstar.uniconv.generated.resources.ml
import io.magicstar.uniconv.generated.resources.mm3
import io.magicstar.uniconv.generated.resources.pt
import io.magicstar.uniconv.generated.resources.yd3
import org.jetbrains.compose.resources.StringResource
import kotlin.math.pow

abstract class Volume(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object KM3: Volume(Res.string.km3,"km3", KM.reference.toDouble().pow(3.0))
object M3: Volume(Res.string.m3,"m3", M.reference.toDouble().pow(3.0))
object DM3: Volume(Res.string.dm3,"dm3", DM.reference.toDouble().pow(3.0))
object CM3: Volume(Res.string.cm3,"cm3", CM.reference.toDouble().pow(3.0))
object MM3: Volume(Res.string.mm3,"mm3", MM.reference.toDouble().pow(3.0))

object KL: Volume(Res.string.kl,"kl", M.reference.toDouble().pow(3.0))
object HL: Volume(Res.string.hl,"hl", KL.reference.toDouble() / 10)
object DAL: Volume(Res.string.dal,"dal", KL.reference.toDouble() / 100)
object L: Volume(Res.string.l,"l", DM.reference.toDouble().pow(3.0))
object DL: Volume(Res.string.hl,"hl", L.reference.toDouble() / 10)
object CL: Volume(Res.string.dal,"dal", L.reference.toDouble() / 100)
object ML: Volume(Res.string.ml,"ml", CM.reference.toDouble().pow(3.0))

object FT3: Volume(Res.string.ft3,"ft3", FT.reference.toDouble().pow(3.0))
object IN3: Volume(Res.string.in3,"in3", IN.reference.toDouble().pow(3.0))
object YD3: Volume(Res.string.yd3,"yd3", YD.reference.toDouble().pow(3.0))
object MI3: Volume(Res.string.mi3,"mi3", MI.reference.toDouble().pow(3.0))

object GAL: Volume(Res.string.gal,"gal (US)", 231 * IN3.reference.toDouble())
object FL_OZ: Volume(Res.string.fl_oz,"fl oz (US)", GAL.reference.toDouble() / 128)
object PINT: Volume(Res.string.pt,"pt (US)", FL_OZ.reference.toDouble() * 16)
object CUP: Volume(Res.string.cup,"cup (US)", FL_OZ.reference.toDouble() * 8)

val volumeUnits = listOf<Unit>(
    KM3, M3, DM3, CM3, MM3,
    KL, HL, DAL, L, DL, CL, ML,
    FT3, IN3, YD3, MI3,
    GAL, FL_OZ, PINT, CUP
)