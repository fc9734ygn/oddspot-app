package ui.util

import kotlinx.serialization.Serializable

@Serializable
class CameraLocationBounds(
    val coordinates: List<LatLong> = listOf(),
    val padding: Int = 0
)
