package ui.screen.onboarding.tutorial

import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel

@Factory
class TutorialScreenModel : BaseScreenModel<TutorialScreenState>(TutorialScreenState.Initial) {

    fun onCheckboxCheckedChange(checked: Boolean) {
        mutableState.update { it.copy(isCheckboxChecked = checked) }
    }
}