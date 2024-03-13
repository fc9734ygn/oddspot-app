package ui.screen.account

import util.Event

data class AccountScreenState(
    val username: String,
    val isLoading: Boolean,
    val changeUsernameDialogState: ChangeUsernameDialogState,
    val event : Event<AccountScreenEvent>?
){
    companion object {
        val Initial = AccountScreenState(
            username = "",
            event = null,
            isLoading = false,
            changeUsernameDialogState = ChangeUsernameDialogState.Initial
        )
    }
}

data class ChangeUsernameDialogState(
    val show: Boolean,
    val input: String,
    val isLoading: Boolean
){
    companion object {
        val Initial = ChangeUsernameDialogState(
            show = false,
            isLoading = false,
            input = ""
        )
    }

}

sealed class AccountScreenEvent {
    data object GoToWelcomeScreen : AccountScreenEvent()
    data object Error : AccountScreenEvent()
}
