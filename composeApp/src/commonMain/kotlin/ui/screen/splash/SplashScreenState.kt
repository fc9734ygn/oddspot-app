package ui.screen.splash

import androidx.compose.runtime.Immutable
import util.Event

@Immutable
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