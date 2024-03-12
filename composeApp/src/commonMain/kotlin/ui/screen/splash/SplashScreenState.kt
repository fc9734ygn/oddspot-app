package ui.screen.splash

import util.Event

data class SplashScreenState(
    val isLoading: Boolean,
    val event: Event<SplashScreenEvent>? = null
) {
    companion object {
        val Initial = SplashScreenState(
            isLoading = true
        )
    }
}

sealed class SplashScreenEvent {
    data object NavigateToGetStarted : SplashScreenEvent()
    data object NavigateToMap : SplashScreenEvent()
    data object NavigateWelcome : SplashScreenEvent()
}