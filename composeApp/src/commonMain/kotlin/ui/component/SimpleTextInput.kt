package ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import ui.util.footnote
import ui.util.input

@Composable
fun SimpleTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        autoCorrect = false
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = input(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        label = {
            Text(
                label,
                style = footnote(),
                color = colorResource(MR.colors.red)
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(MR.colors.red),
            unfocusedBorderColor = colorResource(MR.colors.red),
            textColor = colorResource(MR.colors.white),
            cursorColor = colorResource(MR.colors.red),
            errorCursorColor = colorResource(MR.colors.red),
            errorLeadingIconColor = colorResource(MR.colors.red),
            errorTrailingIconColor = colorResource(MR.colors.red),
            errorLabelColor = colorResource(MR.colors.red),
            placeholderColor = colorResource(MR.colors.red)
        )
    )
}