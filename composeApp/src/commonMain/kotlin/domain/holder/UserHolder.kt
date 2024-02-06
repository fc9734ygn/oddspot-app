package domain.holder

import data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Singleton

@Singleton
class UserHolder(
    userRepository: UserRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    val userFlow = userRepository.getUserFlow()
        .stateIn(scope, SharingStarted.Eagerly, null)

    val user
        get() = userFlow.value

    val isLoggedIn
        get() = user != null
}