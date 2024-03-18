package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import org.koin.compose.KoinContext
import ui.screen.splash.SplashScreen

@Composable
fun App() {
    KoinContext {
        MaterialTheme(colors = MaterialTheme.colors.copy(
            primary = ui.util.Colors.red,
            secondary = ui.util.Colors.white,
            background = ui.util.Colors.background

        )) {
            Navigator(screen = SplashScreen()){
                FadeTransition(it)
            }
        }
    }
}