package io.magicstar.uniconv.test

import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.FT_S
import io.magicstar.uniconv.unit.model.KM_H
import io.magicstar.uniconv.unit.model.KN
import io.magicstar.uniconv.unit.model.MPH
import io.magicstar.uniconv.unit.model.M_S
import io.magicstar.uniconv.unit.model.Unit
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import si.uom.SI
import systems.uom.common.USCustomary
import kotlin.test.assertEquals


class SpeedTest {
    companion object {
        @JvmStatic
        fun expectedResults(): List<Arguments> {
            val unitsList = listOf(
                SI.KILOMETRE_PER_HOUR,
                SI.METRE_PER_SECOND,
                USCustomary.MILE_PER_HOUR,
                USCustomary.KNOT,
                USCustomary.FOOT_PER_SECOND
            )
            val programUnitList = listOf(KM_H, M_S, MPH, KN, FT_S)
            val expected = mutableListOf<Arguments>()
            unitsList.forEach { origin ->
                unitsList.forEach { target ->
                    expected.add(Arguments.of(
                        programUnitList[unitsList.indexOf(origin)],
                        programUnitList[unitsList.indexOf(target)],
                        origin.getConverterTo(target).convert(5)))
                }
            }
            return expected
        }
    }

    @ParameterizedTest
    @MethodSource("expectedResults")
    fun checkConversions(origin: Unit, target: Unit, expectedValue: Number) {
        println("For origin unit ${origin.name} and target unit ${target.name}:")
        val actual = convert(5, origin, target)
        assertEquals(
            expectedValue.toDouble().roundTo(10),
            actual.toDouble().roundTo(10),
            .000005,
            "Expected value was $expectedValue, but got $actual."
        )
    }
}