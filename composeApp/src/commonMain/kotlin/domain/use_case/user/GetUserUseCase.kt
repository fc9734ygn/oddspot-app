package domain.use_case.user

import com.github.michaelbull.result.getOrElse
import com.homato.oddspot.User
import data.repository.UserRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val user = userRepository.getUser().getOrElse {
            emit(Resource.Error())
            return@flow
        }
        if (user == null) {
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(user))
    }
}