package ui.screen.account

import domain.use_case.user.ChangeAvatarUseCase
import domain.use_case.user.ChangeUsernameUseCase
import domain.use_case.user.GetFeedbackEmailBodyUseCase
import domain.use_case.user.GetUserUseCase
import domain.use_case.user.LogoutUseCase
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class AccountScreenModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getFeedbackEmailBodyUseCase: GetFeedbackEmailBodyUseCase,
    private val changeUsernameUseCase: ChangeUsernameUseCase,
    private val changeAvatarUseCase: ChangeAvatarUseCase
) : BaseScreenModel<AccountScreenState>(AccountScreenState.Initial) {

    init {
        getUserData()
    }

    private fun getUserData() {
        getUserUseCase().collectResource(
            onSuccess = { user ->
                updateState {
                    copy(
                        username = user.name,
                        isLoading = false,
                        avatarUrl = user.avatar_url
                    )
                }
            },
            onError = {
                updateState {
                    copy(isLoading = false)
                }
            },
            onLoading = {
                updateState {
                    copy(isLoading = true)
                }
            }
        )
    }

    fun onChangeUsernameClick() {
        updateState {
            copy(
                changeUsernameDialogState = state.value.changeUsernameDialogState.copy(show = true)
            )
        }
    }

    fun onAvatarSelected(avatar: ByteArray) {
        changeAvatarUseCase(avatar).collectResource(
            onSuccess = {
                updateState {
                    copy(
                        isAvatarLoading = false,
                        avatarUrl = it,
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        isAvatarLoading = false,
                        event = Event(AccountScreenEvent.Error)
                    )
                }
            },
            onLoading = {
                updateState {
                    copy(isAvatarLoading = true)
                }
            }
        )
    }

    fun onLogoutClick() {
        logoutUseCase().collectResource(
            onSuccess = {
                updateState {
                    copy(
                        event = Event(AccountScreenEvent.GoToWelcomeScreen),
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        event = Event(AccountScreenEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = {
                updateState { copy(isLoading = true) }
            }
        )
    }

    fun getFeedbackEmailBody(): String {
        return getFeedbackEmailBodyUseCase()
    }

    fun onDismissUsernameChangeDialog() {
        updateState { copy(changeUsernameDialogState = ChangeUsernameDialogState.Initial) }
    }

    fun onUsernameInputChange(username: String) {
        updateState {
            copy(
                changeUsernameDialogState = state.value.changeUsernameDialogState.copy(input = username)
            )
        }
    }

    fun onConfirmUsernameChangeClick() {
        if (state.value.changeUsernameDialogState.input.isEmpty()) return
        changeUsernameUseCase(state.value.changeUsernameDialogState.input)
            .collectResource(
                onSuccess = {
                    updateState {
                        copy(
                            changeUsernameDialogState = state.value.changeUsernameDialogState.copy(
                                isLoading = false,
                                show = false
                            ),
                            username = state.value.changeUsernameDialogState.input,
                        )
                    }
                },
                onError = {
                    updateState {
                        copy(
                            changeUsernameDialogState = state.value.changeUsernameDialogState.copy(
                                isLoading = false,
                                show = false
                            ),
                            event = Event(AccountScreenEvent.Error)
                        )
                    }
                },
                onLoading = {
                    updateState {
                        copy(
                            changeUsernameDialogState = state.value.changeUsernameDialogState.copy(
                                isLoading = true
                            )
                        )
                    }
                }
            )
    }
}