package domain.use_case.spot.model

data class SpotDetail(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val inWishlist: Boolean,
    val amountOfVisits: Int,
    val accessibility: Accessibility,
    val visitImages: List<String>,
    val isLocked: Boolean,
)
