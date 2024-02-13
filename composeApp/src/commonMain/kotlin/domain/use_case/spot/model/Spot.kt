package domain.use_case.spot.model

data class Spot(
    val id: String,
    val title: String,
    val description: String,
    val coordinates: Pair<Double, Double>,
    val imageUrl: String,
    val likes: Int,
    val dislikes: Int,
    val isLiked: Boolean,
    val isDisliked: Boolean,
    val hashtags: List<String>
)