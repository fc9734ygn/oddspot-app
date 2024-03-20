package domain.use_case.spot.model

data class WishlistItemModel(
    val spotId: Int,
    val title: String,
    val mainImage: String,
    val accessibility: Accessibility,
)
