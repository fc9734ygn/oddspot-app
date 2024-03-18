package ui.screen.account

import domain.use_case.user.ChangeUsernameUseCase
import domain.use_case.user.GetFeedbackEmailBodyUseCase
import domain.use_case.user.GetUserUseCase
import domain.use_case.user.LogoutUseCase
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class AccountScreenModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getFeedbackEmailBodyUseCase: GetFeedbackEmailBodyUseCase,
    private val changeUsernameUseCase: ChangeUsernameUseCase
) : BaseScreenModel<AccountScreenState>(AccountScreenState.Initial) {

    init {
        getUserData()
    }

    private fun getUserData() {
        getUserUseCase().collectResource(
            onSuccess = { user ->
                mutableState.update { it.copy(username = user.name, isLoading = false) }
            },
            onError = {
                mutableState.update { it.copy(isLoading = false) }
            },
            onLoading = {
                mutableState.update { it.copy(isLoading = true) }
            }
        )
    }

    fun onChangeUsernameClick() {
        mutableState.update {
            it.copy(
                changeUsernameDialogState = it.changeUsernameDialogState.copy(show = true)
            )
        }
    }

    fun onLogoutClick() {
        logoutUseCase().collectResource(
            onSuccess = {
                mutableState.update {
                    it.copy(
                        event = Event(AccountScreenEvent.GoToWelcomeScreen),
                        isLoading = false
                    )
                }
            },
            onError = {
                mutableState.update {
                    it.copy(
                        event = Event(AccountScreenEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = {
                mutableState.update { it.copy(isLoading = true) }
            }
        )
    }

    fun getFeedbackEmailBody(): String {
        return getFeedbackEmailBodyUseCase()
    }

    fun onDismissUsernameChangeDialog() {
        mutableState.update { it.copy(changeUsernameDialogState = ChangeUsernameDialogState.Initial) }
    }

    fun onUsernameInputChange(username: String) {
        mutableState.update {
            it.copy(
                changeUsernameDialogState = it.changeUsernameDialogState.copy(input = username)
            )
        }
    }

    fun onConfirmUsernameChangeClick() {
        if (state.value.changeUsernameDialogState.input.isEmpty()) return
        changeUsernameUseCase(state.value.changeUsernameDialogState.input)
            .collectResource(
                onSuccess = {
                    mutableState.update {
                        it.copy(
                            changeUsernameDialogState = it.changeUsernameDialogState.copy(
                                isLoading = false,
                                show = false
                            ),
                            username = it.changeUsernameDialogState.input,
                        )
                    }
                },
                onError = {
                    mutableState.update {
                        it.copy(
                            changeUsernameDialogState = it.changeUsernameDialogState.copy(
                                isLoading = false,
                                show = false
                            ),
                            event = Event(AccountScreenEvent.Error)
                        )
                    }
                },
                onLoading = {
                    mutableState.update {
                        it.copy(
                            changeUsernameDialogState = it.changeUsernameDialogState.copy(isLoading = true)
                        )
                    }
                }
            )
    }
}