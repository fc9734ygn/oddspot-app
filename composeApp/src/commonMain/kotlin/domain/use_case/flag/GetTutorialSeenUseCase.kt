package domain.use_case.flag

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.getOrElse
import data.repository.LocalFlagsRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetTutorialSeenUseCase (
    private val flagsRepository: LocalFlagsRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val wasShown = flagsRepository.isTutorialSeen().getOrElse {
            Logger.e("GetTutorialSeenUseCase", it)
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(wasShown))
    }
}