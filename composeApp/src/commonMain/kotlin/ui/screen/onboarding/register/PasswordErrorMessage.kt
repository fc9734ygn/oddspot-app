package ui.screen.onboarding.register

import androidx.compose.runtime.Composable
import domain.use_case.user.model.PasswordError
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.register_error_password_common
import oddspot_app.composeapp.generated.resources.register_error_password_no_lowercase
import oddspot_app.composeapp.generated.resources.register_error_password_no_number
import oddspot_app.composeapp.generated.resources.register_error_password_no_uppercase
import oddspot_app.composeapp.generated.resources.register_error_password_too_long
import oddspot_app.composeapp.generated.resources.register_error_password_too_short
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun getPasswordErrorMessage(error: PasswordError?) : String {
    return when (error) {
        PasswordError.TOO_SHORT -> {
            stringResource(Res.string.register_error_password_too_short)
        }

        PasswordError.TOO_LONG -> {
            stringResource(Res.string.register_error_password_too_long)
        }

        PasswordError.COMMON -> {
            stringResource(Res.string.register_error_password_common)
        }

        PasswordError.NO_UPPERCASE -> {
            stringResource(Res.string.register_error_password_no_uppercase)
        }

        PasswordError.NO_LOWERCASE -> {
            stringResource(Res.string.register_error_password_no_lowercase)
        }

        PasswordError.NO_NUMBER -> {
            stringResource(Res.string.register_error_password_no_number)
        }

        else -> ""
    }
}