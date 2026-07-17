package io.magicstar.uniconv.test

import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.Length
import io.magicstar.uniconv.unit.model.Unit
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import si.uom.SI
import systems.uom.common.USCustomary
import java.math.BigDecimal
import kotlin.test.assertEquals


class LengthTest {
    companion object {
        @JvmStatic
        fun expectedResults(): List<Arguments> {
            val metre = SI.METRE
            val unitsList = listOf(
                metre.divide(1000),
                metre.divide(100),
                metre.divide(10),
                metre,
                metre.multiply(10),
                metre.multiply(100),
                metre.multiply(1000),
                USCustomary.INCH,
                USCustomary.YARD,
                USCustomary.FOOT,
                USCustomary.MILE
            )
            val programUnitList = listOf(Length.MM(), Length.CM(), Length.DM(), Length.M(),
                Length.DAM(), Length.HM(), Length.KM(), Length.IN(), Length.YD(), Length.FT(), Length.MI()
            )
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
        println("For origin unit ${origin.abbreviation} and target unit ${target.abbreviation}:")
        val actual = convert(BigDecimal("5"), origin, target)
        assertEquals(
            expectedValue.toDouble().roundTo(10),
            actual.toDouble().roundTo(10),
            .000005,
            "Expected value was $expectedValue, but got $actual."
        )
    }
}