package domain.use_case.flag

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.LocalFlagsRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class SetTutorialSeenUseCase(
    private val flagsRepository: LocalFlagsRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())
        flagsRepository.setTutorialSeen().mapError {
            Logger.e("SetTutorialSeenUseCase", it)
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(Unit))
    }
}