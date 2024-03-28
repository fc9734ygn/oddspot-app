package data.request

import kotlinx.serialization.Serializable

@Serializable
data class ReportSpotRequest(
    val spotId: Int,
    val reason: String
)
