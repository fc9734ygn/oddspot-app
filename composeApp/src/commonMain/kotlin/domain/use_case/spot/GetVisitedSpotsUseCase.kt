package domain.use_case.spot

import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import domain.holder.UserHolder
import domain.use_case.spot.model.VisitedSpotItemModel
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetVisitedSpotsUseCase(
    private val spotRepository: SpotRepository,
    private val userHolder: UserHolder
) {
    operator fun invoke(): Flow<Resource<List<VisitedSpotItemModel>>> = flow {
        emit(Resource.Loading())

        val visitedSpots = spotRepository.getVisitedSpots(userHolder.user!!.userId)
            .getOrElse {
                emit(Resource.Error())
                return@flow
            }.sortedByDescending { it.visitTimestamp }
            .map { VisitedSpotItemModel.fromEntity(it) }

        emit(Resource.Success(visitedSpots))
    }
}