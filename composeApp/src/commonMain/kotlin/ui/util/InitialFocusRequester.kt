package ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.delay

private const val INITIAL_FOCUS_DELAY = 50L
@Composable
fun InitialFocusRequester(content: @Composable (FocusRequester) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    content(focusRequester)
    LaunchedEffect(Unit) {
        delay(INITIAL_FOCUS_DELAY)
        focusRequester.requestFocus()
    }
}