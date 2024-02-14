package ui.base

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen

abstract class BaseScreen : Screen {

    @Composable
    abstract fun ScreenContent(snackbarHostState: SnackbarHostState)

    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) {
            ScreenContent(snackbarHostState)
        }
    }
}