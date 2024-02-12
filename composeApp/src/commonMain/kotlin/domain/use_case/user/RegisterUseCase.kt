package domain.use_case.user

import com.github.michaelbull.result.mapError
import data.repository.UserRepository
import domain.use_case.user.model.EmailError
import domain.use_case.user.model.PasswordError
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(
    private val userRepository: UserRepository
) {

    fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
        userRepository.register(email, password).mapError {
            emit(Resource.Error())
            return@flow
        }

        userRepository.login(email, password).mapError {
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(Unit))
    }.onStart { emit(Resource.Loading()) }

    fun validateEmail(email: String): EmailError? {
        return if (!isValidEmail(email)) {
            EmailError.PATTERN_ERROR
        } else {
            null
        }
    }

    fun validatePassword(password: String): PasswordError? {
        return when {
            PasswordValidator.isTooShort(password) -> PasswordError.TOO_SHORT
            PasswordValidator.isTooLong(password) -> PasswordError.TOO_LONG
            PasswordValidator.isCommon(password) -> PasswordError.COMMON
            !PasswordValidator.containsUppercase(password) -> PasswordError.NO_UPPERCASE
            !PasswordValidator.containsLowercase(password) -> PasswordError.NO_LOWERCASE
            !PasswordValidator.containsNumber(password) -> PasswordError.NO_NUMBER
            else -> null
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): PasswordError? {
        return if (confirmPassword != password) {
            PasswordError.PASSWORDS_NOT_MATCHING
        } else {
            null
        }
    }
}