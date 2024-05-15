package domain.use_case.spot

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.util.DomainError
import domain.util.Resource
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class VisitSpotUseCase(
    private val spotRepository: SpotRepository
) {
    operator fun invoke(spotId: Int, rating: Boolean, image: ByteArray?)  = flow<Resource<Unit>> {
        emit(Resource.Loading())
        spotRepository.visitSpot(spotId, rating, image).mapError {
            Logger.e("VisitSpotUseCase", it)
            emit(Resource.Error(DomainError.Network(it)))
            return@flow
        }
        spotRepository.updateExploreSpots().mapError {
            Logger.e("VisitSpotUseCase", it)
            // We do not want to expose this error and use cached spots
        }
        emit(Resource.Success(Unit))
    }
}