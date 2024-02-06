
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext
import ui.screen.welcome.WelcomeScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            Navigator(screen = WelcomeScreen())
        }
    }
}