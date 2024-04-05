package domain.use_case.spot

import LocationProvider
import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import data.repository.WishlistRepository
import domain.use_case.spot.model.SpotDetail
import domain.use_case.spot.model.toAccessibility
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import ui.util.Location
import ui.util.distanceInMetersTo

const val MAX_DISTANCE_FROM_SPOT_METERS = 25

@Factory
class GetSpotDetailUseCase(
    private val spotRepository: SpotRepository,
    private val wishlistRepository: WishlistRepository,
    private val locationProvider: LocationProvider
) {

    operator fun invoke(id: Int): Flow<Resource<SpotDetail>> = flow {
        emit(Resource.Loading())

        val spotWithVisits = spotRepository.getSpotWithVisitsBySpotId(id).getOrElse {
            emit(Resource.Error())
            return@flow
        }

        val wishlist = wishlistRepository.getWishlist().getOrElse {
            emit(Resource.Error())
            return@flow
        }

        val currentUserLocation = locationProvider.getUserLocation().getOrElse {
            emit(Resource.Error())
            return@flow
        }

        val spotLocation = Location(spotWithVisits.spot.latitude, spotWithVisits.spot.longitude)

        val spotDetail = SpotDetail(
            id = spotWithVisits.spot.id.toInt(),
            title = spotWithVisits.spot.title,
            description = spotWithVisits.spot.description,
            imageUrl = spotWithVisits.spot.image_url,
            isWishlisted = wishlist.any { it == spotWithVisits.spot.id.toInt() },
            amountOfVisits = spotWithVisits.visits.size,
            accessibility = spotWithVisits.spot.accessibility.toAccessibility(),
            visitImages = spotWithVisits.visits.map { it.image_url },
            isInRange = currentUserLocation.distanceInMetersTo(spotLocation) <= MAX_DISTANCE_FROM_SPOT_METERS
        )

        emit(Resource.Success(spotDetail))
    }
}