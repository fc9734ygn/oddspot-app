package util

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

const val EARTH_RADIUS_METERS = 6371e3 // Earth's radius in meters
const val DEGREES_TO_RADIANS = PI / 180 // Conversion factor from degrees to radians

fun Pair<Double, Double>.distanceInMetersTo(other: Pair<Double, Double>): Float {
    val dLat = (other.first - this.first) * DEGREES_TO_RADIANS
    val dLon = (other.second - this.second) * DEGREES_TO_RADIANS

    val a = sin(dLat / 2).pow(2) +
            cos(this.first * DEGREES_TO_RADIANS) * cos(other.first * DEGREES_TO_RADIANS) *
            sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return (EARTH_RADIUS_METERS * c).toFloat()
}
