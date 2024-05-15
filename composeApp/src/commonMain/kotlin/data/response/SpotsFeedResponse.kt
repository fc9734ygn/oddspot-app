package data.response

import kotlinx.serialization.Serializable

@Serializable
data class SpotsFeedResponse(
    val spotsWithVisitsResponse: List<ExploreSpotWithVisitsResponse>
)

@Serializable
data class ExploreSpotWithVisitsResponse(
    val spot: ExploreSpotResponse,
    val visits: List<ExploreVisitResponse>
)

@Serializable
data class ExploreSpotResponse(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val creatorId: String,
    val pictureUrl: String,
    val createTime: Long,
    val category: String,
    val difficulty: Int,
    val isArea: Boolean
)

@Serializable
data class ExploreVisitResponse(
    val id: Int,
    val spotId: Int,
    val userId: String,
    val visitTime: Long,
    val imageUrl: String?,
    val rating: Boolean
)