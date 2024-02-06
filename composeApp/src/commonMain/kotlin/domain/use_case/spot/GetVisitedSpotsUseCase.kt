package domain.use_case.spot

import data.repository.SpotRepository
import domain.use_case.spot.model.VisitedSpot
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

// Returns a list of VisitedSpots sorted by visit date
@Factory
class GetVisitedSpotsUseCase(
    private val spotRepository: SpotRepository,
) {
    operator fun invoke(): Flow<Resource<List<VisitedSpot>>> = flow {
        emit(Resource.Loading())

        val visitedSpots = spotRepository.getVisitedSpots()?.sortedByDescending { it.visitedOn }

        if (visitedSpots == null) {
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(visitedSpots))
    }
}