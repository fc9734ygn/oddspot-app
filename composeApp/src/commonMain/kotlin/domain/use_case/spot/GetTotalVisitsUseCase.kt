package domain.use_case.spot

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetTotalVisitsUseCase(
    private val spotRepository: SpotRepository
) {
    operator fun invoke(): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())

        val totalVisits = spotRepository.getUserTotalVisits().getOrElse {
            Logger.e("GetTotalVisitsUseCase", it)
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(totalVisits))
    }
}