package ui.screen.onboarding.get_started

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import ui.base.BaseScreen
import ui.component.button.OutlineButton
import ui.screen.onboarding.login.LoginScreen
import ui.screen.onboarding.register.RegisterScreen
import ui.util.body
import ui.util.h1

class GetStartedScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier
                .background(colorResource(MR.colors.background))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(MR.strings.get_started_title),
                style = h1(),
                color = colorResource(MR.colors.white),
                modifier = Modifier.padding(horizontal = 96.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlineButton(
                onClick = { navigator.push(RegisterScreen()) },
                text = stringResource(MR.strings.get_started_signup_button),
                modifier = Modifier.padding(horizontal = 48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            val loginText = buildAnnotatedString {
                append(stringResource(MR.strings.get_started_login_prefix))
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(MR.strings.get_started_login_suffix))
                }
            }
            Text(
                text = loginText,
                style = body(),
                color = colorResource(MR.colors.white),
                modifier = Modifier
                    .clickable { navigator.push(LoginScreen()) }
                    .padding(horizontal = 48.dp)
            )
        }
    }
}