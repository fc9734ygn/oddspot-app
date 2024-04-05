package ui.component.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ShowSnackBar(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionAfter: (() -> Unit)? = null
) {
    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(
            message = message,
        )
        actionAfter?.invoke()
    }
}
