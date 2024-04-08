package domain.use_case.spot.model

import data.response.SubmittedSpot

data class SubmittedSpotItemModel(
    val id: Int,
    val title: String,
    val mainImage: String,
    val accessibility: Accessibility,
    val submissionTimestamp: Long,
    val status: SubmissionStatus
) {
    companion object {
        fun fromSubmittedSpotResponse(spot: SubmittedSpot): SubmittedSpotItemModel {
            return SubmittedSpotItemModel(
                id = spot.id,
                title = spot.title,
                mainImage = spot.pictureUrl,
                accessibility = spot.difficulty.toAccessibility(),
                submissionTimestamp = spot.createTime,
                status = spot.verificationState.toSubmissionStatus()
            )
        }
    }
}