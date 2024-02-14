package ui.screen.explore

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.base.BaseScreen
import ui.util.h1

class MapScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        Text("Map Screen", style = h1(), color = Color.Black)
    }
}