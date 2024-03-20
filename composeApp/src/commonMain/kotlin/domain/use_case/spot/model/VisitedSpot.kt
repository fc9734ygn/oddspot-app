package domain.use_case.spot.model

data class VisitedSpot (
    val id: Int, // id is the same as the object of Spot data class
    val title: String,
    val description: String,

    val visitedOn: Long, // unix timestamp in milliseconds

    val imageByUserUrl: String,

    // can be re-added to be able to like/dislike in visited spots screen or single visited spot screen
//    val likes: Int,
//    val dislikes: Int,
//    val isLiked: Boolean,
//    val isDisliked: Boolean,
    )