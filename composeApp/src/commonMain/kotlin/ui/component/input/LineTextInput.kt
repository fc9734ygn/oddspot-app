package ui.component.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.util.Colors
import ui.util.input

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LineTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    capitalize: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        autoCorrect = false,
        capitalization = if (capitalize) KeyboardCapitalization.Sentences else KeyboardCapitalization.None
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    placeholder: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            modifier = modifier,
            textStyle = input(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(Colors.red)
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = singleLine,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Colors.background,
                    textColor = Colors.white,
                    cursorColor = Colors.red,
                    focusedIndicatorColor = Colors.red,
                    unfocusedIndicatorColor = Colors.red,
                    disabledIndicatorColor = Colors.red,
                    errorIndicatorColor = Colors.red,
                    errorCursorColor = Colors.red
                ),
                placeholder = { Text(placeholder, style = input(), color = Colors.darkGrey) },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Colors.red, thickness = 2.dp)
    }

}