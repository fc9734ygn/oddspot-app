package ui.screen.account

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ui.base.BaseTabScreen

class AccountScreen : BaseTabScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        Text("Account Screen")
    }
}