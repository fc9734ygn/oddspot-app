package ui.screen.explore.detail

import domain.use_case.spot.model.Accessibility

data class SpotDetailSheetState(
    val spotId: Int?,
    val isWishlisted: Boolean,
    val title: String,
    val description: String,
    val mainImage: String,
    val amountOfVisits: Int,
    val accessibility: Accessibility,
    val isLoading: Boolean,
    val isLocked: Boolean,
    val visitImages: List<String>,
    val showReportDialog: Boolean
) {
    companion object {
        val Initial = SpotDetailSheetState(
            spotId = null,
            isWishlisted = false,
            title = "",
            description = "",
            mainImage = "",
            amountOfVisits = 0,
            accessibility = Accessibility.Easy,
            isLoading = false,
            isLocked = false,
            visitImages = emptyList(),
            showReportDialog = false
        )
    }
}