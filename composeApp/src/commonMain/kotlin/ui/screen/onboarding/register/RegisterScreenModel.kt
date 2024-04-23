package ui.screen.onboarding.register

import domain.use_case.user.RegisterUseCase
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class RegisterScreenModel(
    private val registerUseCase: RegisterUseCase
) : BaseScreenModel<RegisterScreenState>(RegisterScreenState.Initial) {

    fun onContinueClick() {
        if (!validateAll()) return

        registerUseCase.register(mutableState.value.email, mutableState.value.password)
            .collectResource(
                onError = { _ ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            event = Event(RegisterEventType.RegistrationError(null))
                        )
                    }
                },
                onLoading = {
                    mutableState.update { it.copy(isLoading = true) }
                },
                onSuccess = {
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            event = Event(RegisterEventType.AccountCreatedEvent)
                        )
                    }
                }
            )
    }

    // TODO: Move to use-case
    private fun validateAll(): Boolean {
        val emailError = registerUseCase.validateEmail(mutableState.value.email)
        val passwordError = registerUseCase.validatePassword(mutableState.value.password)
        val confirmPasswordError = registerUseCase.validateConfirmPassword(
            mutableState.value.password,
            mutableState.value.confirmPassword
        )

        mutableState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError ?: confirmPasswordError,
            )
        }

        return emailError == null && passwordError == null && confirmPasswordError == null
    }

    fun onEmailInputChange(input: String) {
        mutableState.update { it.copy(email = input) }
    }

    fun onPasswordInputChange(input: String) {
        mutableState.update { it.copy(password = input) }
    }

    fun onConfirmPasswordChange(input: String) {
        mutableState.update { it.copy(confirmPassword = input) }
    }

    fun onPasswordPreviewClick() {
        mutableState.update { it.copy(passwordPreview = !mutableState.value.passwordPreview) }
    }
}