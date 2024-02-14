package ui.screen.onboarding.tutorial

import domain.use_case.flag.SetTutorialSeenUseCase
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class TutorialScreenModel(
    private val setTutorialSeenUseCase: SetTutorialSeenUseCase
) : BaseScreenModel<TutorialScreenState>(TutorialScreenState.Initial) {

    fun onCheckboxCheckedChange(checked: Boolean) {
        mutableState.update { it.copy(isCheckboxChecked = checked) }
    }

    fun onNextClick() {
        setTutorialSeenUseCase().collectResource(
            onSuccess = {
                mutableState.update { it.copy(event = Event(TutorialEvent.Success) ) }
            },
            onError = {
                mutableState.update { it.copy(event = Event(TutorialEvent.Error)) }
            }
        )
    }
}