package domain.use_case.spot.model

import com.homato.oddspot.ExploreSpot
import ui.util.Location

data class Spot(
    val id: Int,
    val title: String,
    val description: String,
    val coordinates: Location,
    val imageUrl: String,
    val inWishlist: Boolean,
) {
    companion object {
        fun fromEntity(exploreSpot: ExploreSpot): Spot {
            return Spot(
                id = exploreSpot.id.toInt(),
                title = exploreSpot.title,
                description = exploreSpot.description,
                coordinates = Location(exploreSpot.latitude, exploreSpot.longitude),
                imageUrl = exploreSpot.image_url,
                inWishlist = false
            )
        }
    }
}
