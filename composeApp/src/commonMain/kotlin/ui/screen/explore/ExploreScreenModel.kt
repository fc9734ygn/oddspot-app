package ui.screen.explore

import domain.use_case.spot.GetExploreUseCase
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class ExploreScreenModel(
    private val getExploreUseCase: GetExploreUseCase
) : BaseScreenModel<ExploreScreenState>(ExploreScreenState.Initial) {

    fun getCurrentUserLocation() {
        getExploreUseCase().collectResource(
            onError = {
                mutableState.update { it.copy(event = Event(ExploreScreenEvent.Error)) }
            },
            onLoading = {
                mutableState.update { it.copy(isLoading = true) }
            },
            onSuccess = { domainModel ->
                mutableState.update {
                    it.copy(
                        markers = domainModel.spots.map { spot ->
                            ExploreMarker(
                                id = spot.id,
                                coordinates = spot.coordinates,
                                category = 1 // TODO: Figure out categories
                            )
                        },
                        userCurrentLocation = domainModel.userCurrentLocation,
                        isLoading = false
                    )
                }

            }
        )
    }

}