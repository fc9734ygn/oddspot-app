package data.model

data class VisitedSpot(
    val id: Int,
    val title: String,
    val description: String,
    val mainImage: String,
    val visitTimestamp: Long,
    val accessibility: Int
)