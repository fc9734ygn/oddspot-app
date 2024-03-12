package ui.screen.splash

import domain.use_case.flag.GetTutorialSeenUseCase
import domain.use_case.user.GetInitialUserState
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class SplashScreenModel(
    private val getTutorialSeenUseCase: GetTutorialSeenUseCase,
    private val getInitialUserState: GetInitialUserState
) : BaseScreenModel<SplashScreenState>(SplashScreenState.Initial) {

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
                            event = Event(SplashScreenEvent.NavigateToMap)
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
                        event = if (tutorialWasShown) Event(SplashScreenEvent.NavigateToGetStarted) else Event(SplashScreenEvent.NavigateWelcome)
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
