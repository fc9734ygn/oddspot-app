package ui.screen.account.submitted

import androidx.compose.runtime.Immutable
import domain.use_case.spot.model.SubmittedSpotItemModel
import util.Event

@Immutable
data class SubmittedSpotsScreenState(
    val items: List<SubmittedSpotItemModel>,
    val isLoading: Boolean,
    val event: Event<SubmittedSpotsScreenEvent>?
) {
    companion object {
        val Initial = SubmittedSpotsScreenState(items = emptyList(), isLoading = true, event = null)
    }
}


sealed class SubmittedSpotsScreenEvent {
    data object Error : SubmittedSpotsScreenEvent()
}
