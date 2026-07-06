package io.magicstar.uniconv.unit.model

import io.magicstar.uniconv.generated.resources.C
import io.magicstar.uniconv.generated.resources.F
import io.magicstar.uniconv.generated.resources.K
import io.magicstar.uniconv.generated.resources.Res
import io.magicstar.uniconv.generated.resources.temperature

sealed class Temperature: Magnitude("tp", Res.string.temperature) {

    data object CELSIUS: Unit(Res.string.C,"ºC", 273.15) {
        override fun to(unit: Unit, value: Number): Double {
            return when (unit) {
                is KELVIN -> value.toDouble() + reference.toDouble()
                is FAHRENHEIT -> (value.toDouble() * 9 / 5) + 32
                else -> value.toDouble()
            }
        }
    }
    data object KELVIN: Unit(Res.string.K,"K", 1) {
        override fun to(unit: Unit, value: Number): Double {
            return when (unit) {
                is CELSIUS -> value.toDouble() - CELSIUS.reference.toDouble()
                is FAHRENHEIT -> ((value.toDouble() - CELSIUS.reference.toDouble()) * (9/5)) + 32
                else -> value.toDouble()
            }
        }
    }
    data object FAHRENHEIT: Unit(Res.string.F,"ºF", (9 / 5) - 459.67) {
        override fun to(unit: Unit, value: Number): Double {
            return when (unit) {
                is CELSIUS -> (value.toDouble() - 32) * 5 / 9
                is KELVIN -> ((value.toDouble() - 32) * 5 / 9) + CELSIUS.reference.toDouble()
                else -> value.toDouble()
            }
        }
    }
}