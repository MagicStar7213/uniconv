package io.magicstar.uniconv.unit

import io.magicstar.uniconv.unit.model.Unit
import kotlin.math.floor

fun convert(value: Number, origin: Unit, target: Unit): Number {
    val res = origin.to(target, value)
    return if (floor(res) == res)
        res.toInt()
    else res
}