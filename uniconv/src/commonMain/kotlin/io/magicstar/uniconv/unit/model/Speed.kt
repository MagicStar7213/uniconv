package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.ft_s
import io.magicstar.uniconv.generated.resources.km_h
import io.magicstar.uniconv.generated.resources.kn
import io.magicstar.uniconv.generated.resources.m_s
import io.magicstar.uniconv.generated.resources.mph
import io.magicstar.uniconv.generated.resources.speed

sealed class Speed: Magnitude("sp", Res.string.speed) {

    data object KM_H: Unit(Res.string.km_h,"km/h", Length.KM.reference.toDouble() / Time.H.reference.toDouble())
    data object M_S: Unit(Res.string.m_s,"m/s", Length.M.reference.toDouble() / Time.S.reference.toDouble())
    data object MPH: Unit(Res.string.mph,"mph", Length.MI.reference.toDouble() / Time.H.reference.toDouble())
    data object KN: Unit(Res.string.kn,"kn", (1.852 * Length.KM.reference.toDouble()) / Time.H.reference.toDouble())
    data object FT_S: Unit(Res.string.ft_s,"ft/s", Length.FT.reference.toDouble() / Time.S.reference.toDouble())
}