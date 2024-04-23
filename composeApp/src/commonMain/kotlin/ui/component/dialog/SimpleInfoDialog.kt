package ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ui.util.Colors
import ui.util.body
import ui.util.input

@Composable
fun SimpleInfoDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Column(
            modifier = Modifier.background(
                color = Colors.background,
                shape = RoundedCornerShape(12.dp)
            ).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = input().copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = body().copy(textAlign = TextAlign.Start)
            )
        }
    }
}