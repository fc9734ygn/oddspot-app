package ui.component.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_lock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.Colors
import ui.util.button

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    showLockIcon: Boolean = false
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
        enabled = isEnabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Colors.black
            )
            return@Button
        }
        if (showLockIcon) {
            Icon(
                painterResource(Res.drawable.ic_lock),
                contentDescription = null,
                tint = Colors.black,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Text(
                text = text,
                modifier = Modifier.padding(vertical = 4.dp),
                style = button(),
            )
        }
    }
}