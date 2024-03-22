package data.response

import kotlinx.serialization.Serializable

@Serializable
data class WishlistResponse(
    val spotIds: List<Int>
)