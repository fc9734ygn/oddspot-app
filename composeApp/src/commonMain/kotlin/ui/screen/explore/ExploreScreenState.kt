package ui.screen.explore

import MapControlsEvent
import androidx.compose.runtime.Immutable
import ui.screen.explore.detail.SpotDetailSheetState
import domain.util.Location
import util.Event


@Immutable
data class ExploreScreenState(
    val userCurrentLocation: Location?,
    val markers: List<ExploreMarker>,
    val isLoading: Boolean,
    val event: Event<ExploreScreenEvent>?,
    val spotDetailsSheetState: SpotDetailSheetState = SpotDetailSheetState.Initial,
    val initialCameraPosition: Location?,
    val mapType: OddSpotMapType,
    val mapEvent: Event<MapControlsEvent>?
) {
    companion object {
        val Initial = ExploreScreenState(
            userCurrentLocation = null,
            markers = emptyList(),
            isLoading = true,
            event = null,
            initialCameraPosition = null,
            mapType = OddSpotMapType.SATELLITE,
            mapEvent = null
        )
    }
}

data class ExploreMarker(
    val id: Int,
    val coordinates: Location,
    val category: Int
)

sealed class ExploreScreenEvent{
    data object Error: ExploreScreenEvent()
    data object OpenSpotDetailBottomSheet: ExploreScreenEvent()
    data object ShowReportSuccessSnackbar: ExploreScreenEvent()
    data object CloseSpotDetailBottomSheet: ExploreScreenEvent()
}
