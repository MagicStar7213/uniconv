package io.magicstar.uniconv.test

import kotlin.collections.listOf
import kotlin.collections.mapOf
import kotlin.math.round
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertEquals
import io.magicstar.uniconv.unit.convert
import io.magicstar.uniconv.unit.model.*

class LengthTest {
    @Test
    fun checkSIConversions() {
        val expectedResults: Map<Length, Map<Length, Number>> = mapOf(
            MM to mapOf(
                MM to 5,
                CM to .5,
                DM to .05,
                M to .005,
                DAM to .0005,
                HM to .00005,
                KM to .000005,
                IN to .19685,
                YD to .0054681,
                FT to .016404,
                MI to .0000031069
            ),
            CM to mapOf(
                MM to 50,
                CM to 5,
                DM to .5,
                M to .05,
                DAM to .005,
                HM to .0005,
                KM to .00005,
                IN to 1.9685,
                YD to .054681,
                FT to .16404,
                MI to .000031069
            ),
            DM to mapOf(
                MM to 500,
                CM to 50,
                DM to 5,
                M to .5,
                DAM to .05,
                HM to .005,
                KM to .0005,
                IN to 19.685,
                YD to .54681,
                FT to 1.6404,
                MI to .00031069
            ),
            M to mapOf(
                MM to 5000,
                CM to 500,
                DM to 50,
                M to 5,
                DAM to .5,
                HM to .05,
                KM to .005,
                IN to 196.85,
                YD to 5.4681,
                FT to 16.404,
                MI to .0031069
            ),
            DAM to mapOf(
                MM to 50000,
                CM to 5000,
                DM to 500,
                M to 50,
                DAM to 5,
                HM to .5,
                KM to .05,
                IN to 1968.5,
                YD to 54.681,
                FT to 164.04,
                MI to .031069
            ),
            HM to mapOf(
                MM to 500000,
                CM to 50000,
                DM to 5000,
                M to 500,
                DAM to 50,
                HM to 5,
                KM to .5,
                IN to 19685,
                YD to 546.81,
                FT to 1640.4,
                MI to .31069
            ),
            KM to mapOf(
                MM to 5000000,
                CM to 500000,
                DM to 50000,
                M to 5000,
                DAM to 500,
                HM to 50,
                KM to 5,
                IN to 196850,
                YD to 5468.1,
                FT to 16404,
                MI to 3.1069
            )
        )
        lengthUnits.filterNot { it is IN || it is FT || it is YD || it is MI }.forEach { origin ->
            lengthUnits.forEach { target ->
                assertEquals((expectedResults[origin]!![target]!!.toDouble()* 10.0.pow(10)).roundToInt() / 10.0.pow(10), (convert(5, origin, target).toDouble()* 10.0.pow(10)).roundToInt() / 10.0.pow(10), 0.05)
            }
        }
    }
}