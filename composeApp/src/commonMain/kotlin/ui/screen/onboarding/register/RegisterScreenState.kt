package ui.screen.onboarding.register

import androidx.compose.runtime.Immutable
import domain.use_case.user.model.EmailError
import domain.use_case.user.model.PasswordError
import util.Event


@Immutable
data class RegisterScreenState(
    val email: String,
    val emailError: EmailError?,
    val password: String,
    val confirmPassword: String,
    val passwordError: PasswordError?,
    val isLoading: Boolean,
    val event: Event<RegisterEventType>?,
) {
    companion object {
        val Initial = RegisterScreenState(
            email = "",
            emailError = null,
            password = "",
            confirmPassword = "",
            passwordError = null,
            isLoading = false,
            event = null,
        )
    }
}

sealed class RegisterEventType {
    object AccountCreatedEvent : RegisterEventType()
    class RegistrationError(val message: String?) : RegisterEventType()
}
