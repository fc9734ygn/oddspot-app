package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ui.util.Colors

@Composable
fun MapGradient(
    modifier: Modifier = Modifier,
    endY: Float,
    startY : Float = 0f,
    reverse: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        if (reverse) Color.Transparent else Colors.background,
                        if (reverse) Colors.background else Color.Transparent
                    ),
                    endY = endY,
                    startY = startY
                )
            )
    )
}