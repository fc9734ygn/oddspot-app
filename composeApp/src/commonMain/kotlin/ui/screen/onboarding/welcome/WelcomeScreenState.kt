package ui.screen.onboarding.welcome

import util.Event

data class WelcomeScreenState(
    val isLoading: Boolean,
    val event: Event<WelcomeScreenEvent>? = null
) {
    companion object {
        val Initial = WelcomeScreenState(
            isLoading = true
        )
    }
}

sealed class WelcomeScreenEvent {
    data object NavigateToMap : WelcomeScreenEvent()
    data object NavigateToGetStarted : WelcomeScreenEvent()
}