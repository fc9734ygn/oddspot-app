package domain.use_case.spot.model

data class ExploreModel(
    val spots: List<Spot>,
    val userCurrentLocation: Pair<Double, Double>
)
