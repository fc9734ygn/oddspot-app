package ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.util.Colors
import ui.util.body

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Colors.red
) {
    Box(modifier = modifier.background(color, shape = RoundedCornerShape(32.dp))) {
        Text(
            text = text,
            style = body(),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )
    }
}