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
import io.magicstar.uniconv.generated.resources.volume
import io.magicstar.uniconv.generated.resources.yd3
import kotlin.math.pow

class Volume: Magnitude("vl", Res.string.volume) {

    class KM3: Unit(Res.string.km3,"km3", Length.KM().reference.toDouble().pow(3))
    class M3: Unit(Res.string.m3,"m3", Length.M().reference.toDouble().pow(3))
    class DM3: Unit(Res.string.dm3,"dm3", Length.DM().reference.toDouble().pow(3))
    class CM3: Unit(Res.string.cm3,"cm3", Length.CM().reference.toDouble().pow(3))
    class MM3: Unit(Res.string.mm3,"mm3", Length.MM().reference.toDouble().pow(3))
    
    class KL: Unit(Res.string.kl,"kl", Length.M().reference.toDouble().pow(3))
    class HL: Unit(Res.string.hl,"hl", KL().reference.toDouble() / 10)
    class DAL: Unit(Res.string.dal,"dal", KL().reference.toDouble() / 100)
    class L: Unit(Res.string.l,"l", Length.DM().reference.toDouble().pow(3))
    class DL: Unit(Res.string.hl,"hl", L().reference.toDouble() / 10)
    class CL: Unit(Res.string.dal,"dal", L().reference.toDouble() / 100)
    class ML: Unit(Res.string.ml,"ml", Length.CM().reference.toDouble().pow(3))
    
    class FT3: Unit(Res.string.ft3,"ft3", Length.FT().reference.toDouble().pow(3))
    class IN3: Unit(Res.string.in3,"in3", Length.IN().reference.toDouble().pow(3))
    class YD3: Unit(Res.string.yd3,"yd3", Length.YD().reference.toDouble().pow(3))
    class MI3: Unit(Res.string.mi3,"mi3", Length.MI().reference.toDouble().pow(3))
    
    class GAL: Unit(Res.string.gal,"gal (US)", IN3().reference.toDouble() * 231)
    class FL_OZ: Unit(Res.string.fl_oz,"fl oz (US)", GAL().reference.toDouble() / 128)
    class PINT: Unit(Res.string.pt,"pt (US)", FL_OZ().reference.toDouble() * 16)
    class CUP: Unit(Res.string.cup,"cup (US)", FL_OZ().reference.toDouble() * 8)
}