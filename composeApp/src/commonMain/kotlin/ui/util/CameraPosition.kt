package ui.util

import kotlinx.serialization.Serializable

@Serializable
class CameraPosition(
    val target: LatLong = LatLong(0.0, 0.0),
    val zoom: Float = 0f
)
