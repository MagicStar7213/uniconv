package io.magicstar.uniconv.unit.model

import org.jetbrains.compose.resources.StringResource
import java.math.BigDecimal
import java.math.RoundingMode

sealed class Unit(val name: StringResource, val abbreviation: String, val reference: Number) {
    open fun to(unit: Unit, value: BigDecimal): BigDecimal {
        val scale = if (value.scale() > 7) value.scale() else 7
        return (value * (BigDecimal(reference.toString()).divide(BigDecimal(unit.reference.toString()), 30, RoundingMode.HALF_EVEN))).setScale(scale,
            RoundingMode.HALF_UP)
    }
}