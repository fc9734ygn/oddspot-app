package ui.screen.submit

import androidx.compose.runtime.Immutable
import ui.util.Location
import util.Event

@Immutable
data class SubmitSpotUiState(
    val isLoading: Boolean,
    val title: String,
    val titleError: TitleError?,
    val description: String,
    val descriptionError: DescriptionError?,
    val locationPickerState: LocationPickerState,
    val image: ImageWrapper?,
    val selectedAccessibility: Int,
    val isArea: Boolean,
    val showAccessibilityInfoDialog: Boolean,
    val showAreaInfoDialog: Boolean,
    val event: Event<SubmitSpotEvent>?
){
    companion object {
        val Initial = SubmitSpotUiState(
            title = "",
            titleError = null,
            description = "",
            descriptionError = null,
            isLoading = false,
            locationPickerState = LocationPickerState(),
            image = null,
            selectedAccessibility = 0,
            showAccessibilityInfoDialog = false,
            showAreaInfoDialog = false,
            event = null,
            isArea = false
        )
    }
}

data class LocationPickerState(
    val currentUserLocation: Location? = null,
    val selectedLocation: Location? = currentUserLocation
)

sealed class TitleError {
    data object TooShort : TitleError()
    data object TooLong : TitleError()
}

sealed class DescriptionError {
    data object TooShort : DescriptionError()
    data object TooLong : DescriptionError()
}

sealed class SubmitSpotEvent {
    data object SpotSubmitted : SubmitSpotEvent()
    data object Error : SubmitSpotEvent()
    data object NoImage : SubmitSpotEvent()
}

sealed class SubmitSpotDifficulty {
    data object Easy : SubmitSpotDifficulty()
    data object Average : SubmitSpotDifficulty()
    data object Challenging : SubmitSpotDifficulty()
}
