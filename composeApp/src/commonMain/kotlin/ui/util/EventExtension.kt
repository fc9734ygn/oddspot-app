package ui.util

import androidx.compose.runtime.Composable
import util.Event

@Composable
inline fun <T> Event<T>.Consume(crossinline action: @Composable (T) -> Unit) {
    val value = get()
    if (value != null) {
        action(value)
    }
}