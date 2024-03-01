package ui.component.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.util.Colors
import ui.util.button

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.red,
            contentColor = Colors.black,
            disabledBackgroundColor = Colors.lightGrey,
            disabledContentColor = Colors.darkGrey
        ),
        shape = RoundedCornerShape(32.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        if (isLoading){
            CircularProgressIndicator(
                color = Colors.black
            )
            return@Button
        }
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 4.dp),
            style = button(),
        )
    }
}