package domain.use_case.spot

import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.use_case.spot.model.ReportReason
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class ReportSpotUseCase(
    private val spotRepository: SpotRepository
) {
    operator fun invoke(spotId: Int, reason: ReportReason): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        spotRepository.reportSpot(spotId, reason.toString()).mapError {
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(Unit))
    }

}