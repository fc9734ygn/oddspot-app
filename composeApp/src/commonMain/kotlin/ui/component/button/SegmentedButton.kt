package ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.util.Colors
import ui.util.input

@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius: Dp = 24.dp,
    color: Color = Colors.red,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row(modifier = modifier.fillMaxWidth()) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                },
                shape = getButtonShape(index, items.size, cornerRadius),
                border = BorderStroke(
                    width = 2.dp,
                    color = color
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (selectedIndex.value == index) color else Color.Transparent,
                ),
            ) {
                Text(
                    text = item,
                    color = if (selectedIndex.value == index) Color.White else color.copy(alpha = 0.9f),
                    style = input()
                )
            }
        }
    }
}

private fun getButtonShape(index: Int, size: Int, cornerRadius: Dp): RoundedCornerShape {
    return when (index) {
        0 -> RoundedCornerShape(topStart = cornerRadius, bottomStart = cornerRadius)
        size - 1 -> RoundedCornerShape(topEnd = cornerRadius, bottomEnd = cornerRadius)
        else -> RoundedCornerShape(0.dp)
    }
}