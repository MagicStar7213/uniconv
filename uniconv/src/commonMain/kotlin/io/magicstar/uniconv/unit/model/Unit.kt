package io.magicstar.uniconv.unit.model

import org.jetbrains.compose.resources.StringResource
import java.math.BigDecimal
import java.math.RoundingMode

sealed class Unit(val name: StringResource, val abbreviation: String, val reference: Number) {
    open fun to(unit: Unit, value: BigDecimal): BigDecimal {
        return value * (BigDecimal(reference.toString()).divide(BigDecimal(unit.reference.toString()), 20,
            RoundingMode.HALF_EVEN))
    }
}