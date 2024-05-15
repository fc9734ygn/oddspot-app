package domain.use_case.spot

import LocationProvider
import co.touchlab.kermit.Logger
import data.repository.SpotRepository
import data.repository.WishlistRepository
import domain.use_case.spot.model.SpotDetail
import domain.use_case.spot.model.toAccessibility
import domain.util.Location
import domain.util.Resource
import domain.util.distanceInMetersTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import util.toBoolean

const val SPOT_RADIUS_METERS = 25
const val AREA_RADIUS_METERS = 100

@Factory
class GetSpotDetailUseCase(
    private val spotRepository: SpotRepository,
    private val wishlistRepository: WishlistRepository,
    private val locationProvider: LocationProvider
) {

    operator fun invoke(id: Int): Flow<Resource<SpotDetail>> = flow {
        emit(Resource.Loading())

        val spotWithVisitsFlow = spotRepository.getSpotWithVisitsFlow(id)
        val wishlistFlow = wishlistRepository.getWishlistFlow()
        val currentUserLocationFlow = locationProvider.getUserLocationFlow()

        combine(
            spotWithVisitsFlow,
            wishlistFlow,
            currentUserLocationFlow
        ) { spotWithVisits, wishlist, currentUserLocation ->

            val spotLocation = Location(spotWithVisits.spot.latitude, spotWithVisits.spot.longitude)
            val spotDetail = SpotDetail(
                id = spotWithVisits.spot.id.toInt(),
                title = spotWithVisits.spot.title,
                description = spotWithVisits.spot.description,
                imageUrl = spotWithVisits.spot.image_url,
                isWishlisted = wishlist.any { it == spotWithVisits.spot.id.toInt() },
                amountOfVisits = spotWithVisits.visits.size,
                accessibility = spotWithVisits.spot.accessibility.toAccessibility(),
                visitImages = spotWithVisits.visits.mapNotNull { it.image_url },
                isInRange = currentUserLocation.distanceInMetersTo(spotLocation) <= SPOT_RADIUS_METERS,
                isArea = spotWithVisits.spot.is_area.toBoolean(),
                likes = spotWithVisits.visits.filter { it.rating.toBoolean() }.size,
                dislikes = spotWithVisits.visits.filter { !it.rating.toBoolean() }.size,
            )
            spotDetail
        }.catch {
            Logger.e("GetSpotDetailUseCase", it)
            emit(Resource.Error())
        }.distinctUntilChanged().collect {
            emit(Resource.Success(it))
        }
    }
}