package io.magicstar.uniconv.unit.model

abstract class Temperature(override val name: String, override val reference: Number): Unit

object CELSIUS: Temperature("ºC", 273.15) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is KELVIN -> value.toDouble() + reference.toDouble()
            is FARENHEIT -> (value.toDouble() * (9 / 5)) + 32
            else -> value.toDouble()
        }
    }
}
object KELVIN: Temperature("K", 1) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is CELSIUS -> value.toDouble() - CELSIUS.reference.toDouble()
            is FARENHEIT -> ((value.toDouble() - CELSIUS.reference.toDouble()) * (9/5)) + 32
            else -> value.toDouble()
        }
    }
}
object FARENHEIT: Temperature("ºF", (9 / 5) - 459.67) {
    override fun to(unit: Unit, value: Number): Double {
        return when (unit) {
            is CELSIUS -> (value.toDouble() - 32) * (5 / 9)
            is KELVIN -> ((value.toDouble() - 32) * (5 / 9)) + CELSIUS.reference.toDouble()
            else -> value.toDouble()
        }
    }
}

val temperatureUnits = listOf(CELSIUS, KELVIN, FARENHEIT)