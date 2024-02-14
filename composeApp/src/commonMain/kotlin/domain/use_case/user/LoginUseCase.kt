package domain.use_case.user

import com.github.michaelbull.result.mapError
import data.repository.UserRepository
import domain.util.DomainError
import domain.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(email: String, password: String) = flow {
        userRepository.login(email, password).mapError {
            emit(Resource.Error(DomainError.Network(throwable = it)))
            return@flow
        }

        emit(Resource.Success(Unit))
    }.onStart { emit(Resource.Loading()) }
}