package ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.component.button.PrimaryButton
import ui.component.input.LineTextInput
import ui.util.Colors
import ui.util.body
import ui.util.h3

@OptIn(ExperimentalResourceApi::class)
@Composable
fun InputDialog(
    onDismiss: () -> Unit,
    title: String,
    description: String? = null,
    inputValue: String,
    onInputValueChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    isLoading: Boolean
) {

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
                style = h3()
            )
            Spacer(modifier = Modifier.height(8.dp))
            description?.let {
                Text(
                    text = it,
                    style = body()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LineTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = inputValue,
                onValueChange = onInputValueChange
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                text = stringResource(Res.string.ok),
                onClick = onConfirmClick,
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading
            )
        }
    }
}