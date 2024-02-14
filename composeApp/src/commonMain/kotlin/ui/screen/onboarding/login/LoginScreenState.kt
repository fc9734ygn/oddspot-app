package ui.screen.onboarding.login

import androidx.compose.runtime.Immutable
import util.Event

@Immutable
data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val event: Event<LoginEventType>? = null,
    val passwordPreview: Boolean = false
)

sealed class LoginEventType {
    data object Success : LoginEventType()
    class Error(val message: String?) : LoginEventType()
}
