package io.magicstar.uniconv.unit

import io.magicstar.uniconv.unit.model.*
import io.magicstar.uniconv.unit.model.Unit
import kotlin.math.floor

fun convert(value: Double, origin: Unit, target: Unit): Number {
    val res = if (origin is SI<*> && target is Imperial<*>)
        origin.to(target, origin.toImperial(value))
    else if (origin is Imperial<*> && target is SI<*>)
        origin.to(target, origin.toSI(value))
    else
        origin.to(target, value)
    return if (floor(res) == res)
        res.toInt()
    else res
}

fun updateMagnitudes(magnitudes: List<String>, magnitude: String): List<Unit> {
    return when (magnitude) {
        magnitudes.elementAt(0) -> lengthUnits
        magnitudes.elementAt(1) -> weightUnits
        magnitudes.elementAt(2) -> timeUnits
        magnitudes.elementAt(3) -> temperatureUnits
        magnitudes.elementAt(4) -> surfaceUnits
        magnitudes.elementAt(5) -> volumeUnits
        magnitudes.elementAt(6) -> speedUnits
        else -> lengthUnits
    }
}