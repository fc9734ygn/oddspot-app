package ui.screen.onboarding.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import oddspot_app.composeapp.generated.resources.img_welcome_bottom
import oddspot_app.composeapp.generated.resources.img_welcome_top
import oddspot_app.composeapp.generated.resources.welcome_button
import oddspot_app.composeapp.generated.resources.welcome_subtitle
import oddspot_app.composeapp.generated.resources.welcome_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.screen.onboarding.tutorial.TutorialExploreScreen
import ui.util.Colors
import ui.util.h1
import ui.util.h3

class WelcomeScreen : BaseScreen() {

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
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(painterResource(Res.drawable.img_welcome_top), contentDescription = null)
                }
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp),
                    text = stringResource(Res.string.welcome_title),
                    color = Colors.white,
                    style = h1()
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(painterResource(Res.drawable.img_welcome_bottom), contentDescription = null)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 48.dp),
                    text = stringResource(Res.string.welcome_subtitle),
                    color = Colors.white,
                    style = h3()
                )
            }
            PrimaryButton(
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .padding(bottom = 48.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                text = stringResource(Res.string.welcome_button),
                onClick = { navigator.push(TutorialExploreScreen()) }
            )
        }
    }
}