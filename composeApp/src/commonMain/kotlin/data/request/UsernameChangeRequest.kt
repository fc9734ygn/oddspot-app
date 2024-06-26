package data.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameChangeRequest(
    val username: String
)
