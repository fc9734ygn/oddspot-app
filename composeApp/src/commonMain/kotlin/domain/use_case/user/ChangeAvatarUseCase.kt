package domain.use_case.user

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.getOrElse
import data.repository.UserRepository
import domain.util.DomainError
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
class ChangeAvatarUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(avatar: ByteArray) : Flow<Resource<String?>> = flow {
        val avatarUrl = userRepository.changeAvatar(avatar).getOrElse {
            Logger.e("ChangeAvatarUseCase", it)
            emit(Resource.Error(DomainError.Network(throwable = it)))
            return@flow
        }

        emit(Resource.Success(avatarUrl))
    }.onStart { emit(Resource.Loading()) }
}