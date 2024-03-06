package ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import ui.component.button.PrimaryButton

@Composable
fun SimpleDialog(
    title: String,
    subtitle: String? = null,
    annotatedSubtitle: AnnotatedString? = null,
    iconId: Int? = null,
    confirmButtonText: String? = null,
    dismissButtonText: String? = null,
    confirmButtonEnabled: Boolean = true,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    DialogLayout(onDismiss = onDismiss, iconId = iconId) {
        Text(
            text = title,
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        if (annotatedSubtitle != null) {
            Text(
                text = annotatedSubtitle,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        content()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            dismissButtonText?.let {
                PrimaryButton(
                    enabled = confirmButtonEnabled,
                    onClick = onConfirm,
                    text = dismissButtonText ?: "abc",
                )
            }

            PrimaryButton(
                enabled = confirmButtonEnabled,
                onClick = onConfirm,
                text = confirmButtonText ?: "abc",
            )
        }
    }
}