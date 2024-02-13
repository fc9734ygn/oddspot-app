package ui.screen.onboarding.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import ui.component.SimpleTextInput
import ui.util.InitialFocusRequester

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        val focusManager = LocalFocusManager.current
        val passwordPreview = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
                .padding(bottom = 128.dp),
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
                )
            }
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
                    Icon(painter = painterResource(if (passwordPreview.value) MR.images.eye_slash else MR.images.eye),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            passwordPreview.value = !passwordPreview.value
                        })
                },
            )
            Button(
                onClick = { screenModel.onContinueClick() }
            ) {
                Text(
                    text = stringResource(MR.strings.login_button),
                )
            }
        }
    }
}