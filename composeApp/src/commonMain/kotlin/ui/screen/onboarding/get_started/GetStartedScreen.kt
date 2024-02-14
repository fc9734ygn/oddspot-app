package ui.screen.onboarding.get_started

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.stringResource
import ui.base.BaseScreen
import ui.screen.onboarding.login.LoginScreen
import ui.screen.onboarding.register.RegisterScreen

class GetStartedScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(MR.strings.application_name)
            )

            Button(
                onClick = { navigator.push(RegisterScreen()) },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = stringResource(MR.strings.welcome_signup_button))
            }

            Text(
                text = stringResource(MR.strings.welcome_login_button),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clickable { navigator.push(LoginScreen()) }
            )
        }
    }
}