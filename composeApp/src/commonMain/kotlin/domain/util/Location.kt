package domain.util


import JavaSerializable
import kotlinx.serialization.Serializable
import ui.util.LatLong
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

// Define a data class to store latitude and longitude coordinates
@Serializable
data class Location(val latitude: Double, val longitude: Double) : JavaSerializable

fun Location.isLocationValid(): Boolean {
    return latitude != 0.0 && longitude != 0.0
}

fun LatLong.toLocation(): Location {
    return Location(latitude, longitude)
}

fun Location.toLatLong(): LatLong {
    return LatLong(latitude, longitude)
}

const val EARTH_RADIUS_METERS = 6371e3 // Earth's radius in meters
const val DEGREES_TO_RADIANS = PI / 180 // Conversion factor from degrees to radians

fun Location.distanceInMetersTo(other: Location): Float {
    val dLat = (other.latitude - this.latitude) * DEGREES_TO_RADIANS
    val dLon = (other.longitude - this.longitude) * DEGREES_TO_RADIANS

    val a = sin(dLat / 2).pow(2) +
            cos(this.latitude * DEGREES_TO_RADIANS) *
            cos(other.latitude * DEGREES_TO_RADIANS) *
            sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return (EARTH_RADIUS_METERS * c).toFloat()
}

