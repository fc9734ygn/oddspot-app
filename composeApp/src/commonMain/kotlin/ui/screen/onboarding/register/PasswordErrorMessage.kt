package ui.screen.onboarding.register

import androidx.compose.runtime.Composable
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.stringResource
import domain.use_case.user.model.PasswordError


@Composable
fun getPasswordErrorMessage(error: PasswordError?) : String {
    return when (error) {
        PasswordError.TOO_SHORT -> {
            stringResource(MR.strings.register_error_password_too_short)
        }

        PasswordError.TOO_LONG -> {
            stringResource(MR.strings.register_error_password_too_long)
        }

        PasswordError.COMMON -> {
            stringResource(MR.strings.register_error_password_common)
        }

        PasswordError.NO_UPPERCASE -> {
            stringResource(MR.strings.register_error_password_no_uppercase)
        }

        PasswordError.NO_LOWERCASE -> {
            stringResource(MR.strings.register_error_password_no_lowercase)
        }

        PasswordError.NO_NUMBER -> {
            stringResource(MR.strings.register_error_password_no_number)
        }

        else -> ""
    }
}