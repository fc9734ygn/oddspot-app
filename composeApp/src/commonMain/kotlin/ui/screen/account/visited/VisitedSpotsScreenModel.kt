package ui.screen.account.visited

import domain.use_case.spot.GetVisitedSpotsUseCase
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class VisitedSpotsScreenModel(
    private val getVisitedSpotsUseCase: GetVisitedSpotsUseCase
) : BaseScreenModel<VisitedSpotsScreenState>(VisitedSpotsScreenState.Initial) {

    init {
        getData()
    }

    private fun getData() {
        getVisitedSpotsUseCase().collectResource(
            onSuccess = {
                updateState {
                    copy(
                        isLoading = false,
                        items = it
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        isLoading = false,
                        event = Event(VisitedSpotsScreenEvent.Error)
                    )
                }
            },
            onLoading = {
                updateState {
                    copy(isLoading = true)
                }
            }
        )
    }
}