package ui.component.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GenericErrorSnackbar(
    snackbarHostState: SnackbarHostState,
) {
    ShowSnackBar(
        snackbarHostState = snackbarHostState,
        message = stringResource(MR.strings.error_generic)
    )
}