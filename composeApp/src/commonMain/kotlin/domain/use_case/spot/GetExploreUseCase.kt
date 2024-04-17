package domain.use_case.spot

import LocationProvider
import co.touchlab.kermit.Logger
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.use_case.spot.model.ExploreModel
import domain.use_case.spot.model.SpotMarkerModel
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import ui.util.Location
import ui.util.distanceInMetersTo

@Factory
class GetExploreUseCase(
    private val spotRepository: SpotRepository,
    private val locationProvider: LocationProvider
) {

    // Returns a list of unknown spots sorted by distance to the user's current location
    operator fun invoke(): Flow<Resource<ExploreModel>> = flow {
        emit(Resource.Loading())

        val currentUserLocation = locationProvider.getUserLocation().getOrElse {
            Logger.w("GetExploreUseCase  $it")
            emit(Resource.Error())
            return@flow
        }

        spotRepository.updateExploreSpots().mapError {
            Logger.e("GetExploreUseCase", it)
            // We do not want to expose this error and use cached spots
        }

        spotRepository.getExploreSpotsFlow().collect { spotsWithVisits ->
            val filteredAndSortedSpotEntities = spotsWithVisits
                .map { it.spot }
                .sortedBy { spot ->
                    val spotLocation = Location(spot.latitude, spot.longitude)
                    spotLocation.distanceInMetersTo(
                        Location(currentUserLocation.latitude, currentUserLocation.longitude)
                    )
                }
            val sortedSpotMarkerModels = filteredAndSortedSpotEntities.map { SpotMarkerModel.fromEntity(it) }

            emit(
                Resource.Success(
                    ExploreModel(
                        sortedSpotMarkerModels,
                        currentUserLocation
                    )
                )
            )
        }
    }
}