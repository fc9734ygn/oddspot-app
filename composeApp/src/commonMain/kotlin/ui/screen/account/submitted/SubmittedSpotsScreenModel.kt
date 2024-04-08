package ui.screen.account.submitted

import domain.use_case.spot.GetSubmittedSpotsUseCase
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class SubmittedSpotsScreenModel(
    private val getSubmittedSpotsUseCase: GetSubmittedSpotsUseCase
) : BaseScreenModel<SubmittedSpotsScreenState>(SubmittedSpotsScreenState.Initial) {

    init {
        getData()
    }

    private fun getData() {
        getSubmittedSpotsUseCase().collectResource(
            onSuccess = {
                updateState {
                    copy(
                        items = it,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        event = Event(SubmittedSpotsScreenEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = { updateState { copy(isLoading = true) } }
        )
    }
}
