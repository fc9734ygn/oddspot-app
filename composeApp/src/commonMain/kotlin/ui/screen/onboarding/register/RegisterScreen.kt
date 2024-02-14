package ui.screen.onboarding.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import domain.use_case.user.model.EmailError
import domain.use_case.user.model.PasswordError
import ui.base.BaseScreen
import ui.component.SimpleTextInput
import ui.util.InitialFocusRequester

class RegisterScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val screenModel = getScreenModel<RegisterScreenModel>()
        val state: RegisterScreenState = screenModel.state.collectAsState().value
        val focusManager = LocalFocusManager.current
        val passwordPreview = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InitialFocusRequester {
                SimpleTextInput(
                    value = state.email,
                    onValueChange = screenModel::onEmailInputChange,
                    label = stringResource(MR.strings.create_account_email_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(it),
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        autoCorrect = false
                    ),
                    isError = state.emailError != null
                )
                AnimatedVisibility(visible = state.emailError == EmailError.PATTERN_ERROR) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = when (state.emailError) {
                            EmailError.PATTERN_ERROR -> stringResource(MR.strings.create_account_error_email)
                            else -> ""
                        },
                        color = Color.Red,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            val passwordFieldError =
                state.passwordError != null && state.passwordError != PasswordError.PASSWORDS_NOT_MATCHING
            SimpleTextInput(
                value = state.password,
                onValueChange = screenModel::onPasswordInputChange,
                label = stringResource(MR.strings.create_account_password_hint),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                visualTransformation = if (passwordPreview.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(if (passwordPreview.value) MR.images.eye_slash else MR.images.eye),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                passwordPreview.value = !passwordPreview.value
                            }
                    )
                },
                isError = passwordFieldError
            )
            AnimatedVisibility(visible = passwordFieldError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = getPasswordErrorMessage(state.passwordError),
                    color = Color.Red,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body1
                )
            }
            SimpleTextInput(
                value = state.confirmPassword,
                onValueChange = screenModel::onConfirmPasswordChange,
                label = stringResource(MR.strings.create_account_confirm_password_hint),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions { screenModel.onContinueClick() },
                visualTransformation = if (passwordPreview.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(if (passwordPreview.value) MR.images.eye_slash else MR.images.eye),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordPreview.value = !passwordPreview.value }
                    )
                },
                isError = state.passwordError == PasswordError.PASSWORDS_NOT_MATCHING
            )
            AnimatedVisibility(visible = state.passwordError == PasswordError.PASSWORDS_NOT_MATCHING) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (state.passwordError) {
                        PasswordError.PASSWORDS_NOT_MATCHING -> {
                            stringResource(MR.strings.create_account_error_password_passwords_not_matching)
                        }

                        else -> ""
                    },
                    color = Color.Red,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body1
                )
            }
            Button(
                onClick = screenModel::onContinueClick
            ) {
                Text(
                    text = stringResource(MR.strings.create_account_continue),
                )
            }
        }
    }
}