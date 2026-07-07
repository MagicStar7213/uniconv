package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.ft_s
import io.magicstar.uniconv.generated.resources.km_h
import io.magicstar.uniconv.generated.resources.kn
import io.magicstar.uniconv.generated.resources.m_s
import io.magicstar.uniconv.generated.resources.mph
import io.magicstar.uniconv.generated.resources.speed

class Speed: Magnitude("sp", Res.string.speed) {

    class KM_H: Unit(Res.string.km_h,"km/h", Length.KM().reference.toDouble() / Time.H().reference.toDouble())
    class M_S: Unit(Res.string.m_s,"m/s", Length.M().reference.toDouble() / Time.S().reference.toDouble())
    class MPH: Unit(Res.string.mph,"mph", Length.MI().reference.toDouble() / Time.H().reference.toDouble())
    class KN: Unit(Res.string.kn,"kn", (1.852 * Length.KM().reference.toDouble()) / Time.H().reference.toDouble())
    class FT_S: Unit(Res.string.ft_s,"ft/s", Length.FT().reference.toDouble() / Time.S().reference.toDouble())
}