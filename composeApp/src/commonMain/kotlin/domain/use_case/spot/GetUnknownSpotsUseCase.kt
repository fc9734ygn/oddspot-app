package domain.use_case.spot

import data.repository.LocationProvider
import data.repository.SpotRepository
import domain.use_case.spot.model.Spot
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import util.distanceInMetersTo

@Factory
class GetUnknownSpotsUseCase(
    private val spotRepository: SpotRepository,
    private val locationProvider: LocationProvider
) {

    // Returns a list of unknown spots sorted by distance to the user's current location
    operator fun invoke(): Flow<Resource<Pair<List<Spot>, Pair<Double, Double>>>> = flow {
        emit(Resource.Loading())

        val currentUserLocation = locationProvider.getCurrentLocation()
        if (currentUserLocation == null) {
            emit(Resource.Error())
            return@flow
        }

        val spots = spotRepository.getUnknownSpots(currentUserLocation)
        val sortedSpots = spots.sortedBy { it.coordinates.distanceInMetersTo(currentUserLocation) }

        emit(Resource.Success(Pair(sortedSpots, currentUserLocation)))
    }

}