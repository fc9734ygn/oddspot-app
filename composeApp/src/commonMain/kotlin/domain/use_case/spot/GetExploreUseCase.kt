package domain.use_case.spot

import LocationProvider
import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.use_case.spot.model.ExploreModel
import domain.use_case.spot.model.SpotMarkerModel
import domain.util.DomainError
import domain.util.Location
import domain.util.Resource
import domain.util.distanceInMetersTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetExploreUseCase(
    private val spotRepository: SpotRepository,
    private val locationProvider: LocationProvider
) {

    // Returns a list of unknown spots sorted by distance to the user's current location
    operator fun invoke(): Flow<Resource<ExploreModel>> = flow {
        emit(Resource.Loading())

        spotRepository.updateExploreSpots().mapError {
            Logger.e("GetExploreUseCase", it)
            // We do not want to expose this error and use cached spots
        }

        combine(
            locationProvider.getUserLocationFlow(),
            spotRepository.getExploreSpotsFlow()
        ) { userLocation, spotsWithVisits ->
            val filteredAndSortedSpotEntities = spotsWithVisits
                .map { it.spot }
                .sortedBy { spot ->
                    val spotLocation = Location(spot.latitude, spot.longitude)
                    spotLocation.distanceInMetersTo(
                        Location(userLocation.latitude, userLocation.longitude)
                    )
                }
            val sortedSpotMarkerModels =
                filteredAndSortedSpotEntities.map { SpotMarkerModel.fromEntity(it) }
            Resource.Success(
                ExploreModel(
                    sortedSpotMarkerModels,
                    userLocation
                )
            )
        }.catch {
            emit(Resource.Error(DomainError.Generic))
        }.collect {
            emit(it)
        }
    }
}