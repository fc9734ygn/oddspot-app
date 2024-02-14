package ui.screen.onboarding.tutorial

data class TutorialScreenState(
    val isCheckboxChecked: Boolean,
) {
    companion object {
        val Initial = TutorialScreenState(
            isCheckboxChecked = false,
        )
    }
}
