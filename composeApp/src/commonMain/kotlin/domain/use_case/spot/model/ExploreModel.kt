package domain.use_case.spot.model

import ui.util.Location

data class ExploreModel(
    val spotMarkerModels: List<SpotMarkerModel>,
    val userCurrentLocation: Location
)
