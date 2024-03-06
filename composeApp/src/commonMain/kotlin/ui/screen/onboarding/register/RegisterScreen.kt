package ui.screen.onboarding.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.use_case.user.model.EmailError
import domain.use_case.user.model.PasswordError
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.eye
import oddspot_app.composeapp.generated.resources.eye_slash
import oddspot_app.composeapp.generated.resources.register_button
import oddspot_app.composeapp.generated.resources.register_confirm_password_label
import oddspot_app.composeapp.generated.resources.register_email_label
import oddspot_app.composeapp.generated.resources.register_error_email
import oddspot_app.composeapp.generated.resources.register_error_password_passwords_not_matching
import oddspot_app.composeapp.generated.resources.register_password_label
import oddspot_app.composeapp.generated.resources.register_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.component.input.RoundTextInput
import ui.component.snackbar.GenericErrorSnackbar
import ui.screen.explore.ExploreScreen
import ui.util.Colors
import ui.util.Consume
import ui.util.InitialFocusRequester
import ui.util.footnote
import ui.util.h1

class RegisterScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val screenModel = getScreenModel<RegisterScreenModel>()
        val state: RegisterScreenState = screenModel.state.collectAsState().value
        val focusManager = LocalFocusManager.current
        val navigator = LocalNavigator.currentOrThrow

        state.event?.Consume {
            when (it) {
                is RegisterEventType.AccountCreatedEvent -> {
                    navigator.replaceAll(ExploreScreen())
                }

                is RegisterEventType.RegistrationError -> GenericErrorSnackbar(snackbarHostState)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.register_title),
                style = h1(),
                color = Colors.white,
                modifier = Modifier.padding(horizontal = 96.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            InitialFocusRequester {
                RoundTextInput(
                    value = state.email,
                    onValueChange = screenModel::onEmailInputChange,
                    label = stringResource(Res.string.register_email_label),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(it)
                        .padding(horizontal = 48.dp),
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        autoCorrect = false
                    ),
                )
            }
            AnimatedVisibility(visible = state.emailError == EmailError.PATTERN_ERROR) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                        .padding(start = 16.dp)
                        .padding(top = 4.dp),
                    text = when (state.emailError) {
                        EmailError.PATTERN_ERROR -> stringResource(Res.string.register_error_email)
                        else -> ""
                    },
                    style = footnote().copy(textAlign = TextAlign.Start),
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            val passwordFieldError =
                state.passwordError != null && state.passwordError != PasswordError.PASSWORDS_NOT_MATCHING
            RoundTextInput(
                value = state.password,
                onValueChange = screenModel::onPasswordInputChange,
                label = stringResource(Res.string.register_password_label),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                visualTransformation = if (state.passwordPreview) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (state.passwordPreview) Res.drawable.eye_slash
                            else Res.drawable.eye
                        ),
                        contentDescription = null,
                        tint = Colors.red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                screenModel.onPasswordPreviewClick()
                            }
                    )
                },
            )
            AnimatedVisibility(visible = passwordFieldError) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp)
                        .padding(start = 16.dp).padding(top = 4.dp),
                    text = getPasswordErrorMessage(state.passwordError),
                    style = footnote().copy(textAlign = TextAlign.Start),
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            RoundTextInput(
                value = state.confirmPassword,
                onValueChange = screenModel::onConfirmPasswordChange,
                label = stringResource(Res.string.register_confirm_password_label),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions { screenModel.onContinueClick() },
                visualTransformation = if (state.passwordPreview) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (state.passwordPreview) Res.drawable.eye_slash
                            else Res.drawable.eye
                        ),
                        contentDescription = null,
                        tint = Colors.red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { screenModel.onPasswordPreviewClick() }
                    )
                },
            )
            AnimatedVisibility(visible = state.passwordError == PasswordError.PASSWORDS_NOT_MATCHING) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 48.dp)
                        .padding(start = 16.dp)
                        .padding(top = 4.dp),
                    text = when (state.passwordError) {
                        PasswordError.PASSWORDS_NOT_MATCHING -> {
                            stringResource(Res.string.register_error_password_passwords_not_matching)
                        }

                        else -> ""
                    },
                    style = footnote().copy(textAlign = TextAlign.Start),
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
            PrimaryButton(
                onClick = screenModel::onContinueClick,
                text = stringResource(Res.string.register_button),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                isLoading = state.isLoading
            )
        }
    }
}