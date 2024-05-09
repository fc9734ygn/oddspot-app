package ui.screen.submit.location_picker

import MapControlsEvent
import androidx.compose.runtime.Immutable
import domain.util.Location
import util.Event

@Immutable
data class LocationPickerScreenState(
    val userLocation: Location?,
    val event : Event<MapControlsEvent>?,
    val selectedLocation: Location?
) {
    companion object {
        val Initial = LocationPickerScreenState(
            userLocation = null,
            event = null,
            selectedLocation = null
        )
    }
}
