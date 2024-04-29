package domain.use_case.spot.model

import com.homato.oddspot.ExploreSpot
import domain.util.Location

data class SpotMarkerModel(
    val id: Int,
    val coordinates: Location,
    val category: Int = 1 // TODO: Figure out categories
) {
    companion object {
        fun fromEntity(exploreSpot: ExploreSpot): SpotMarkerModel {
            return SpotMarkerModel(
                id = exploreSpot.id.toInt(),
                coordinates = Location(exploreSpot.latitude, exploreSpot.longitude)
            )
        }
    }
}
