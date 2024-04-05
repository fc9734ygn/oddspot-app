package ui.component.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect

//@Composable
//fun ShowSnackBarWithActionAfter(
//    snackbarHostState: SnackbarHostState,
//    message: String,
//    action: () -> Unit
//) {
//    DisposableEffect(snackbarHostState) {
//        snackbarHostState.showSnackbar(
//            message = message,
//        )
//        onDispose {
//            action.invoke()
//        }
//    }
//}
