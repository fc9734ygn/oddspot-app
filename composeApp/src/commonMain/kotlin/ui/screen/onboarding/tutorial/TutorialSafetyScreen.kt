package ui.screen.onboarding.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.tutorial_button
import oddspot_app.composeapp.generated.resources.tutorial_safety_description
import oddspot_app.composeapp.generated.resources.tutorial_safety_subtitle
import oddspot_app.composeapp.generated.resources.tutorial_safety_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.util.Colors
import ui.util.body
import ui.util.h1
import ui.util.h3

class TutorialSafetyScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .background(color = Colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .height(300.dp)
                    .width(200.dp)
                    .align(Alignment.TopCenter)
                    .background(color = Colors.red)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 128.dp),
                    text = stringResource(Res.string.tutorial_safety_title),
                    color = Colors.white,
                    style = h1()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 48.dp),
                    text = stringResource(Res.string.tutorial_safety_description),
                    color = Colors.white,
                    style = body()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 80.dp),
                    text = stringResource(Res.string.tutorial_safety_subtitle),
                    color = Colors.white,
                    style = h3()
                )
                Spacer(modifier = Modifier.height(40.dp))
                PrimaryButton(
                    modifier = Modifier
                        .padding(horizontal = 48.dp)
                        .padding(bottom = 48.dp)
                        .fillMaxWidth(),
                    text = stringResource(Res.string.tutorial_button),
                    onClick = { navigator.push(TutorialPrivacyScreen()) }
                )
            }
        }
    }
}