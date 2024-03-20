package ui.screen.account.visited

import androidx.compose.runtime.Immutable
import domain.use_case.spot.model.VisitedSpotItemModel
import util.Event

@Immutable
data class VisitedSpotsScreenState(
    val isLoading: Boolean,
    val items: List<VisitedSpotItemModel>,
    val event: Event<VisitedSpotsScreenEvent>?
) {
    companion object {
        val Initial = VisitedSpotsScreenState(
            isLoading = false,
            items = emptyList(),
            event = null
        )
    }
}

sealed class VisitedSpotsScreenEvent {
    data object Error : VisitedSpotsScreenEvent()
}