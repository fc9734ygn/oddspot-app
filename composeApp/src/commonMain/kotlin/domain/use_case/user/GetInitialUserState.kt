package domain.use_case.user

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.getOrElse
import data.repository.UserRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetInitialUserState(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val user = userRepository.getUser().getOrElse {
            Logger.e("GetInitialUserState", it)
            emit(Resource.Error())
            return@flow
        }
        val userIsLoggedIn = user != null
        emit(Resource.Success(userIsLoggedIn))
    }
}