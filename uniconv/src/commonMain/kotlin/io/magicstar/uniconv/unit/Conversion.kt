package io.magicstar.uniconv.unit

import io.magicstar.uniconv.unit.model.Unit
import java.math.BigDecimal

fun convert(value: BigDecimal, origin: Unit, target: Unit): BigDecimal {
    val res = origin.to(target, value)
    return res.stripTrailingZeros()
}