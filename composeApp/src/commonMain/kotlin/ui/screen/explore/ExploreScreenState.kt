package ui.screen.explore

import androidx.compose.runtime.Immutable
import ui.util.Location
import util.Event


@Immutable
data class ExploreScreenState(
    val userCurrentLocation: Location?,
    val markers: List<ExploreMarker>,
    val isLoading: Boolean,
    val event: Event<ExploreScreenEvent>?
) {
    companion object {
        val Initial = ExploreScreenState(
            userCurrentLocation = null,
            markers = emptyList(),
            isLoading = true,
            event = null
        )
    }
}

data class ExploreMarker(
    val id: String,
    val coordinates: Pair<Double, Double>,
    val category: Int
)

sealed class ExploreScreenEvent{
    data object Error: ExploreScreenEvent()
}
