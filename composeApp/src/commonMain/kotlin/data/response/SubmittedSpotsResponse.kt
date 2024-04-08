package data.response

import kotlinx.serialization.Serializable

@Serializable
data class SubmittedSpotsResponse(
    val spots: List<SubmittedSpot>
)

@Serializable
data class SubmittedSpot(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val creatorId: String,
    val pictureUrl: String,
    val createTime: Long,
    val verificationState: String,
    val category: String,
    val difficulty: Int,
    val isActive: Boolean,
)