package ui.screen.explore.visit

import domain.use_case.spot.GetTotalVisitsUseCase
import domain.use_case.spot.VisitSpotUseCase
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import ui.screen.submit.ImageWrapper
import util.Event

@Factory
class VisitScreenModel(
    getTotalVisitsUseCase: GetTotalVisitsUseCase,
    private val visitSpotUseCase: VisitSpotUseCase
) : BaseScreenModel<VisitScreenState>(VisitScreenState.Initial) {

    init {
        getTotalVisitsUseCase().collectResource(
            onSuccess = { totalVisits ->
                updateState {
                    copy(userTotalVisits = totalVisits)
                }
            },
            onError = {
                updateState {
                    copy(event = Event(VisitScreenEvent.GenericError))
                }
            }
        )
    }

    fun onImageSelected(data: ByteArray) {
        updateState {
            copy(image = ImageWrapper(data))
        }
    }

    fun onLikeClick() {
        updateState {
            copy(visitRating = VisitRating.Like)
        }
    }

    fun onDislikeClick() {
        updateState {
            copy(visitRating = VisitRating.Dislike)
        }
    }

    fun onMarkAsVisitedClick(spotId: Int) {
        if (state.value.visitRating == null) {
            updateState {
                copy(event = Event(VisitScreenEvent.NoRatingError))
            }
            return
        }

        updateState {
            copy(userTotalVisits = state.value.userTotalVisits?.plus(1))
        }

        visitSpotUseCase(
            spotId = spotId,
            rating = state.value.visitRating == VisitRating.Like,
            image = state.value.image?.data
        ).collectResource(
            onSuccess = {
                updateState {
                    copy(event = Event(VisitScreenEvent.VisitSuccess), isLoading = false)
                }
            },
            onError = {
                updateState {
                    copy(
                        event = Event(VisitScreenEvent.GenericError),
                        isLoading = false,
                        userTotalVisits = state.value.userTotalVisits?.minus(1)
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