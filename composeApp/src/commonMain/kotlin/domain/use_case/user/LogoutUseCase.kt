package domain.use_case.user

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.UserRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class LogoutUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        userRepository.deleteLocalData().mapError {
            Logger.e("LogoutUseCase", it)
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(Unit))
    }
}