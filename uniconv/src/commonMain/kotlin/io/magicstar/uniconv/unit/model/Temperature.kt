package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.C
import io.magicstar.uniconv.generated.resources.F
import io.magicstar.uniconv.generated.resources.K
import io.magicstar.uniconv.generated.resources.Res
import org.jetbrains.compose.resources.StringResource

abstract class Temperature(override val name: StringResource, override val abbreviation: String, override val reference: Number): Unit

object CELSIUS: Temperature(Res.string.C,"ºC", 273.15) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is KELVIN -> value.toDouble() + reference.toDouble()
            is FAHRENHEIT -> (value.toDouble() * 9 / 5) + 32
            else -> value.toDouble()
        }
    }
}
object KELVIN: Temperature(Res.string.K,"K", 1) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is CELSIUS -> value.toDouble() - CELSIUS.reference.toDouble()
            is FAHRENHEIT -> ((value.toDouble() - CELSIUS.reference.toDouble()) * (9/5)) + 32
            else -> value.toDouble()
        }
    }
}
object FAHRENHEIT: Temperature(Res.string.F,"ºF", (9 / 5) - 459.67) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is CELSIUS -> (value.toDouble() - 32) * 5 / 9
            is KELVIN -> ((value.toDouble() - 32) * 5 / 9) + CELSIUS.reference.toDouble()
            else -> value.toDouble()
        }
    }
}

val temperatureUnits = listOf(CELSIUS, KELVIN, FAHRENHEIT)