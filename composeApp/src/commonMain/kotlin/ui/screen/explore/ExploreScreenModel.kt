package ui.screen.explore

import cafe.adriel.voyager.core.model.screenModelScope
import domain.use_case.spot.GetExploreUseCase
import domain.use_case.spot.GetSpotDetailUseCase
import domain.use_case.spot.model.ReportReason
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import ui.screen.explore.detail.SpotDetailSheetState
import util.Event

@Factory
class ExploreScreenModel(
    private val getExploreUseCase: GetExploreUseCase,
    private val getSpotDetailUseCase: GetSpotDetailUseCase
) : BaseScreenModel<ExploreScreenState>(ExploreScreenState.Initial) {

    fun getData() {
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
                        isLoading = false,
                        cameraPosition = domainModel.userCurrentLocation
                    )
                }

            }
        )
    }

    fun onMarkerClick(id: Int) {
        getSpotDetailUseCase(id).collectResource(
            onSuccess = { spotDetails ->
                updateState {
                    copy(
                        spotDetailsSheetState = spotDetailsSheetState.copy(
                            spotId = id,
                            title = spotDetails.title,
                            description = spotDetails.description,
                            mainImage = spotDetails.imageUrl,
                            amountOfVisits = spotDetails.amountOfVisits,
                            accessibility = spotDetails.accessibility,
                            isLoading = false,
                            isLocked = spotDetails.isLocked,
                            visitImages = spotDetails.visitImages
                        ),
                        event = Event(ExploreScreenEvent.OpenSpotDetailBottomSheet),
                        isLoading = false,
                        cameraPosition = mutableState.value.markers.find { it.id == id }?.coordinates
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        event = Event(ExploreScreenEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = {
                updateState {
                    copy(
                        isLoading = true
                    )
                }
            }
        )
    }

    fun onSpotDetailsSheetDismiss() {
        updateState {
            copy(
                spotDetailsSheetState = SpotDetailSheetState.Initial,
            )
        }
    }

    fun onWishlistClick(spotId: Int): () -> Unit = {
        // TODO: Implement
    }

    fun onReportDialogDismiss() {
        updateState {
            copy(
                spotDetailsSheetState = spotDetailsSheetState.copy(
                    showReportDialog = false
                )
            )
        }
    }

    fun onReportClick() {
        updateState {
            copy(
                spotDetailsSheetState = spotDetailsSheetState.copy(
                    showReportDialog = true
                )
            )
        }
    }

    fun onReportReason(reason: ReportReason) {
        screenModelScope.launch {
            updateState {
                copy(
                    spotDetailsSheetState = spotDetailsSheetState.copy(
                        showReportDialog = false
                    ),
                    event = Event(ExploreScreenEvent.CloseSpotDetailBottomSheet)
                )
            }
            delay(500L) // TODO: Implement api call
            updateState {
                copy(
                    spotDetailsSheetState = SpotDetailSheetState.Initial,
                    event = Event(ExploreScreenEvent.ShowReportSuccessSnackbar)
                )
            }
        }
    }
}