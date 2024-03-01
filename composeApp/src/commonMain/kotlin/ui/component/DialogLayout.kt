package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.eye_slash
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DialogLayout(
    iconId: Int? = null,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (iconId != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.Red,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                        .padding(vertical = 36.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(38.dp),
                        painter = painterResource(Res.drawable.eye_slash),
                        contentDescription = null,
                    )
                }
            }

            DialogLayoutContent(hasIcon = iconId != null, content = content)
        }
    }
}

@Composable
fun DialogLayoutContent(
    hasIcon: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = Color.Blue,
                shape = if (hasIcon) RoundedCornerShape(
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                ) else RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        content()
    }
}