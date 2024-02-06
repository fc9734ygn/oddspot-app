package domain.use_case.spot

import data.repository.SpotRepository
import domain.use_case.spot.model.SubmittedSpot
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

// Returns a list of SubmittedSpots sorted by visit date
@Factory
class GetSubmittedSpotsUseCase(
    private val spotRepository: SpotRepository,
) {
    operator fun invoke(): Flow<Resource<List<SubmittedSpot>>> = flow {
        emit(Resource.Loading())

        val submittedSpots = spotRepository.getSubmittedSpots()?.sortedByDescending { it.submittedOn }

        if (submittedSpots == null) {
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(submittedSpots))
    }
}