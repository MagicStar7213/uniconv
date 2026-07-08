package io.magicstar.uniconv.unit

import io.magicstar.uniconv.unit.model.Unit
import java.math.BigDecimal

fun convert(value: BigDecimal, origin: Unit, target: Unit): Number {
    val res = origin.to(target, value)
    return if (res.toBigInteger() == res)
        res.toBigInteger()
    else res
}