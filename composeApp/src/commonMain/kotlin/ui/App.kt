package ui
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinContext
import ui.screen.explore.ExploreScreen

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            Navigator(screen = ExploreScreen())
        }
    }
}