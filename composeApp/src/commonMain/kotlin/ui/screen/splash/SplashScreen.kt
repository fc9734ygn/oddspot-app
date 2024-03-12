package ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.img_splash
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.base.BaseScreen
import ui.screen.explore.ExploreScreen
import ui.screen.onboarding.get_started.GetStartedScreen
import ui.screen.onboarding.welcome.WelcomeScreen
import ui.util.Colors
import ui.util.Consume

class SplashScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {

        val screenModel = getScreenModel<SplashScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        state.event?.Consume {
            when (it) {
                SplashScreenEvent.NavigateToGetStarted -> navigator.replaceAll(GetStartedScreen())
                SplashScreenEvent.NavigateToMap -> navigator.replaceAll(ExploreScreen())
                SplashScreenEvent.NavigateWelcome ->  navigator.replaceAll(WelcomeScreen())
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(Colors.red)
        ) {
            Image(
                painter = painterResource(Res.drawable.img_splash),
                contentDescription = null
            )
        }
    }
}