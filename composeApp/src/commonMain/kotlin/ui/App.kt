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
        MaterialTheme {
            Navigator(screen = SplashScreen()){
                FadeTransition(it)
            }
        }
    }
}