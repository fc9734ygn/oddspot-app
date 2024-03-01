package ui.component.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.error_generic
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GenericErrorSnackbar(
    snackbarHostState: SnackbarHostState,
) {
    ShowSnackBar(
        snackbarHostState = snackbarHostState,
        message = stringResource(Res.string.error_generic)
    )
}