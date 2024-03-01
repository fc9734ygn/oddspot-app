package ui.screen.onboarding.login

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.eye
import oddspot_app.composeapp.generated.resources.eye_slash
import oddspot_app.composeapp.generated.resources.login_button
import oddspot_app.composeapp.generated.resources.login_title
import oddspot_app.composeapp.generated.resources.register_email_label
import oddspot_app.composeapp.generated.resources.register_password_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.SimpleTextInput
import ui.component.button.PrimaryButton
import ui.component.snackbar.GenericErrorSnackbar
import ui.screen.explore.ExploreScreen
import ui.util.Colors
import ui.util.Consume
import ui.util.InitialFocusRequester
import ui.util.h1

class LoginScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val screenModel = getScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        val focusManager = LocalFocusManager.current
        val navigator = LocalNavigator.currentOrThrow

        state.event?.Consume {
            when (it) {
                is LoginEventType.Success -> {
                    navigator.replaceAll(ExploreScreen())
                }

                is LoginEventType.Error -> GenericErrorSnackbar(snackbarHostState)
            }
        }
        Column(
            modifier = Modifier
                .background(Colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.login_title),
                style = h1(),
                modifier = Modifier.padding(horizontal = 96.dp),
                color = Colors.white
            )
            Spacer(modifier = Modifier.height(24.dp))
            InitialFocusRequester {
                SimpleTextInput(
                    value = state.email,
                    onValueChange = screenModel::onEmailInputChange,
                    label = stringResource(Res.string.register_email_label),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                        .focusRequester(it),
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        autoCorrect = false
                    ),
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            SimpleTextInput(
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
                    Icon(painter = painterResource(if (state.passwordPreview) Res.drawable.eye_slash else Res.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            screenModel.onPasswordPreviewClick()
                        },
                        tint = Colors.red
                    )
                },
            )
            Spacer(modifier = Modifier.height(48.dp))
            PrimaryButton(
                onClick = { screenModel.onContinueClick() },
                text = stringResource(Res.string.login_button),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                isLoading = state.isLoading
            )
        }
    }
}