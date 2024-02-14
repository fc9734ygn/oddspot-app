package ui.screen.onboarding.welcome

import domain.holder.UserHolder
import domain.use_case.flag.GetTutorialSeenUseCase
import domain.use_case.user.GetInitialUserState
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class WelcomeScreenModel(
    private val getTutorialSeenUseCase: GetTutorialSeenUseCase,
    private val userHolder: UserHolder,
    private val getInitialUserState: GetInitialUserState
) : BaseScreenModel<WelcomeScreenState>(WelcomeScreenState.Initial) {

    init {
        getState()
    }

    private fun getState() {
        getInitialUserState().collectResource(
            onLoading = { mutableState.update { it.copy(isLoading = true) } },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            event = Event(WelcomeScreenEvent.NavigateToMap)
                        )
                    }
                } else {
                    checkTutorialState()
                }
            },
            onError = {
                mutableState.update {
                    it.copy(isLoading = false)
                }
            }
        )
    }

    private fun checkTutorialState() {
        getTutorialSeenUseCase().collectResource(
            onLoading = { mutableState.update { it.copy(isLoading = true) } },
            onSuccess = { tutorialWasShown ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        event = if (tutorialWasShown) Event(WelcomeScreenEvent.NavigateToGetStarted) else null
                    )
                }
            },
            onError = {
                mutableState.update {
                    it.copy(isLoading = false)
                }
            }
        )
    }
}
