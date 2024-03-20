package ui.screen.onboarding.tutorial

import androidx.compose.runtime.Immutable
import util.Event

@Immutable
data class TutorialScreenState(
    val isCheckboxChecked: Boolean,
    val event: Event<TutorialEvent>?
) {
    companion object {
        val Initial = TutorialScreenState(
            isCheckboxChecked = false,
            event = null
        )
    }
}

sealed class TutorialEvent {
    data object Success : TutorialEvent()
    data object Error : TutorialEvent()
}
