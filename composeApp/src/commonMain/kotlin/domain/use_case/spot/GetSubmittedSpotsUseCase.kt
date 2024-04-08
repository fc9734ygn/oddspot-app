package domain.use_case.spot

import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import domain.use_case.spot.model.SubmittedSpotItemModel
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetSubmittedSpotsUseCase(
    private val spotRepository: SpotRepository,
) {
    operator fun invoke(): Flow<Resource<List<SubmittedSpotItemModel>>> = flow {
        emit(Resource.Loading())

        val submittedSpots = spotRepository.getSubmittedSpots().getOrElse {
            emit(Resource.Error())
            return@flow
        }.map { SubmittedSpotItemModel.fromSubmittedSpotResponse(it) }
            .sortedByDescending { it.submissionTimestamp }

        emit(Resource.Success(submittedSpots))
    }
}