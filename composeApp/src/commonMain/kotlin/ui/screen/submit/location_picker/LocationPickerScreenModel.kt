package ui.screen.submit.location_picker

import LocationProvider
import MapControlsEvent
import cafe.adriel.voyager.core.model.screenModelScope
import domain.util.Location
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class LocationPickerScreenModel(
    private val locationProvider: LocationProvider
) : BaseScreenModel<LocationPickerScreenState>(LocationPickerScreenState.Initial) {

    init {
        screenModelScope.launch {
            locationProvider.getUserLocationFlow().collect {
                updateState {
                    copy(
                        userLocation = it
                    )
                }
            }
        }
    }

    fun onAnimateToUserLocationClick() {
        val userLocation = state.value.userLocation ?: return
        updateState {
            copy(
                event = Event(MapControlsEvent.AnimateToLocation(userLocation))
            )
        }
    }

    fun onLocationRefined(location: Location) {
        updateState {
            copy(
                selectedLocation = location
            )
        }
    }
}