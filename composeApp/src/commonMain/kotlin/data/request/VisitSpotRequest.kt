package data.request

import kotlinx.serialization.Serializable

@Serializable
data class VisitSpotRequest(
    val id: Int,
    val rating: Boolean
)