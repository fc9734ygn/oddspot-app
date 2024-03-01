package ui.screen.onboarding.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.welcome_button
import oddspot_app.composeapp.generated.resources.welcome_subtitle
import oddspot_app.composeapp.generated.resources.welcome_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.screen.explore.ExploreScreen
import ui.screen.onboarding.get_started.GetStartedScreen
import ui.screen.onboarding.tutorial.TutorialExploreScreen
import ui.util.Colors
import ui.util.Consume
import ui.util.h1
import ui.util.h3

class WelcomeScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<WelcomeScreenModel>()
        val state by screenModel.state.collectAsState()

        state.event?.Consume {
            when(it) {
                WelcomeScreenEvent.NavigateToGetStarted -> {
                    navigator.push(GetStartedScreen())
                }
                WelcomeScreenEvent.NavigateToMap -> {
                    navigator.replaceAll(ExploreScreen())
                }
            }
        }
        Box(
            modifier = Modifier
                .background(color = Colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            if (state.isLoading) {
//                CircularProgressIndicator(color = Colors.red)
//                LinearProgressIndicator()
                return@Box
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp),
                    text = stringResource(Res.string.welcome_title),
                    color = Colors.white,
                    style = h1()
                )
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