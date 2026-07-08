package io.magicstar.uniconv.unit.model

import org.jetbrains.compose.resources.StringResource
import java.math.BigDecimal

sealed class Unit(val name: StringResource, val abbreviation: String, val reference: Number) {
    open fun to(unit: Unit, value: BigDecimal): BigDecimal {
        return value * (reference.toDouble().toBigDecimal() / unit.reference.toDouble().toBigDecimal())
    }
}