package ui.screen.submit

import LocationProvider
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.michaelbull.result.getOrElse
import domain.use_case.spot.SubmitSpotUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import domain.util.Location
import util.Event

const val MIN_TITLE_LENGTH = 1
const val MAX_TITLE_LENGTH = 50
const val MIN_DESCRIPTION_LENGTH = 1
const val MAX_DESCRIPTION_LENGTH = 200

@Factory
class SubmitSpotScreenModel(
    private val locationProvider: LocationProvider,
    private val submitSpotUseCase: SubmitSpotUseCase
) : BaseScreenModel<SubmitSpotUiState>(SubmitSpotUiState.Initial) {

    init {
        getUserLocation()
    }

    private fun getUserLocation() {
        screenModelScope.launch {
            val userLocation = locationProvider.getUserLocation().getOrElse {
                mutableState.update { it.copy(event = Event(SubmitSpotEvent.Error)) }
                return@launch
            }

            mutableState.update {
                it.copy(
                    locationPickerState = it.locationPickerState.copy(
                        currentUserLocation = userLocation
                    )
                )
            }
        }
    }

    fun onTitleChange(value: String) {
        mutableState.update { it.copy(title = value) }
    }

    fun onDescriptionChange(value: String) {
        mutableState.update { it.copy(description = value) }
    }

    fun onLocationRefined(location: Location) {
        mutableState.update {
            it.copy(
                locationPickerState = it.locationPickerState.copy(
                    selectedLocation = location
                )
            )
        }
    }

    fun onSubmitClick() {
        val location = state.value.locationPickerState.selectedLocation
            ?: state.value.locationPickerState.currentUserLocation
        val imageData = state.value.image?.data
        val title = state.value.title
        val description = state.value.description

        if (imageData == null) {
            mutableState.update { it.copy(event = Event(SubmitSpotEvent.NoImage), isLoading = false) }
            return
        }
        if (location == null) {
            mutableState.update { it.copy(event = Event(SubmitSpotEvent.Error), isLoading = false) }
            return
        }
        if (title.length < MIN_TITLE_LENGTH) {
            mutableState.update { it.copy(titleError = TitleError.TooShort, isLoading = false) }
            return
        }
        if (title.length > MAX_TITLE_LENGTH) {
            mutableState.update { it.copy(titleError = TitleError.TooLong, isLoading = false) }
            return
        }
        if (description.length < MIN_DESCRIPTION_LENGTH) {
            mutableState.update { it.copy(descriptionError = DescriptionError.TooShort, isLoading = false) }
            return
        }
        if (description.length > MAX_DESCRIPTION_LENGTH) {
            mutableState.update { it.copy(descriptionError = DescriptionError.TooLong, isLoading = false) }
            return
        }

        submitSpotUseCase(
            title = title,
            description = description,
            location = location,
            difficulty = state.value.selectedAccessibility,
            image = imageData,
            isArea = state.value.isArea
        ).collectResource(
            onSuccess = {
                mutableState.update {
                    it.copy(
                        event = Event(SubmitSpotEvent.SpotSubmitted),
                        isLoading = false
                    )
                }
            },
            onError = {
                mutableState.update {
                    it.copy(
                        event = Event(SubmitSpotEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = {
                mutableState.update { it.copy(isLoading = true) }
            }
        )
    }

    fun onDifficultySelected(difficulty: Int) {
        mutableState.update {
            it.copy(selectedAccessibility = difficulty)
        }
    }

    fun onAccessibilityInfoClick() {
        mutableState.update {
            it.copy(showAccessibilityInfoDialog = true)
        }
    }

    fun onAreaInfoClick() {
        mutableState.update {
            it.copy(showAreaInfoDialog = true)
        }
    }

    fun onAreaInfoDialogDismiss() {
        mutableState.update {
            it.copy(showAreaInfoDialog = false)
        }
    }

    fun onAreaSwitchChange(isArea: Boolean) {
        mutableState.update {
            it.copy(isArea = isArea)
        }
    }

    fun onAccessibilityInfoDialogDismiss() {
        mutableState.update {
            it.copy(showAccessibilityInfoDialog = false)
        }
    }

    fun onImageSelected(data: ByteArray){
        mutableState.update {
            it.copy(image = ImageWrapper(data))
        }
    }
}