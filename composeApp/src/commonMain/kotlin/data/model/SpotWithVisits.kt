package data.model

import com.homato.oddspot.ExploreSpot
import com.homato.oddspot.ExploreVisit

data class SpotWithVisits(
    val spot: ExploreSpot,
    val visits: List<ExploreVisit>
)

