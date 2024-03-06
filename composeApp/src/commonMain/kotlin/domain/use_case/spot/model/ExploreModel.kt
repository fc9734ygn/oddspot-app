package domain.use_case.spot.model

import ui.util.Location

data class ExploreModel(
    val spots: List<Spot>,
    val userCurrentLocation: Location
)
