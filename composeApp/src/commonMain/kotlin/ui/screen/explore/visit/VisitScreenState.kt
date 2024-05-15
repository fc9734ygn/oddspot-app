package ui.screen.explore.visit

import androidx.compose.runtime.Immutable
import ui.screen.submit.ImageWrapper
import util.Event

@Immutable
data class VisitScreenState(
    val image: ImageWrapper?,
    val visitRating: VisitRating?,
    val userTotalVisits: Int?,
    val isLoading: Boolean,
    val event: Event<VisitScreenEvent>?
) {
    companion object {
        val Initial = VisitScreenState(
            image = null,
            visitRating = null,
            userTotalVisits = null,
            isLoading = false,
            event = null
        )
    }
}

sealed class VisitRating {
    data object Like : VisitRating()
    data object Dislike : VisitRating()
}

sealed class VisitScreenEvent {
    data object GenericError : VisitScreenEvent()
    data object NoRatingError : VisitScreenEvent()
    data object VisitSuccess : VisitScreenEvent()
}
