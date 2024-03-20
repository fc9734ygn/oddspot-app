package domain.use_case.spot.model

import data.model.VisitedSpot

data class VisitedSpotItemModel(
    val id: Int,
    val title: String,
    val mainImage: String,
    val accessibility: Accessibility
) {
    companion object {
        fun fromEntity(visitedSpot: VisitedSpot): VisitedSpotItemModel {
            return VisitedSpotItemModel(
                id = visitedSpot.id,
                title = visitedSpot.title,
                mainImage = visitedSpot.mainImage,
                accessibility = visitedSpot.accessibility.toAccessibility()
            )
        }
    }
}