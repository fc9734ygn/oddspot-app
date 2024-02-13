package ui.screen.onboarding.login

import androidx.compose.runtime.Immutable
import util.Event

@Immutable
data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val event: Event<LoginEventType>? = null
)

sealed class LoginEventType {
    object Success : LoginEventType()
    class Error(val message: String?) : LoginEventType()
}
