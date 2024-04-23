package ui.screen.onboarding.login

import domain.use_case.user.LoginUseCase
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class LoginScreenModel (
    private val loginUseCase: LoginUseCase
) : BaseScreenModel<LoginScreenState>(LoginScreenState()) {

    fun onEmailInputChange(input: String) {
        mutableState.update { it.copy(email = input) }
    }

    fun onPasswordInputChange(input: String) {
        mutableState.update { it.copy(password = input) }
    }

    fun onContinueClick() {
        loginUseCase.invoke(mutableState.value.email, mutableState.value.password).collectResource(
            onSuccess = {
                mutableState.update { it.copy(isLoading = false, event = Event(LoginEventType.Success)) }
            },
            onError = { _ ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        event = Event(LoginEventType.Error(null))
                    )
                }
            },
            onLoading = {
                mutableState.update { it.copy(isLoading = true) }
            }
        )
    }

    fun onPasswordPreviewClick() {
        mutableState.update { it.copy(passwordPreview = !mutableState.value.passwordPreview) }
    }
}