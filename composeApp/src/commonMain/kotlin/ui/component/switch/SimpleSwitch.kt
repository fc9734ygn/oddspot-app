package ui.component.switch

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.util.Colors

@Composable
fun SimpleSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled : Boolean = true
){
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Colors.red,
            checkedTrackColor = Colors.red,
            uncheckedThumbColor = Colors.lightGrey,
            uncheckedTrackColor = Colors.lightGrey,
        )
    )
}