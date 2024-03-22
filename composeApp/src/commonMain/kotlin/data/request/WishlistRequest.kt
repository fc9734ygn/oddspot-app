package data.request

import kotlinx.serialization.Serializable

@Serializable
data class WishlistRequest(
    val spotId: Int
)
