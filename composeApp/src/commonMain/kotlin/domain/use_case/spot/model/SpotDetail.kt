package domain.use_case.spot.model

data class SpotDetail(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isWishlisted: Boolean,
    val amountOfVisits: Int,
    val accessibility: Accessibility,
    val visitImages: List<String>,
    val isInRange: Boolean,
)
