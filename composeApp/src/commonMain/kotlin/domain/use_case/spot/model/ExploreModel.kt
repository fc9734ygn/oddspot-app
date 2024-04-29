package domain.use_case.spot.model

import domain.util.Location

data class ExploreModel(
    val spotMarkerModels: List<SpotMarkerModel>,
    val userCurrentLocation: Location
)
