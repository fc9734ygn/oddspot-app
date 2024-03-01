package ui.component.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_checkbox_checked
import oddspot_app.composeapp.generated.resources.ic_checkbox_unchecked
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PrimaryCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Image(
        painter = painterResource(
            if (checked) Res.drawable.ic_checkbox_checked
            else Res.drawable.ic_checkbox_unchecked
        ),
        modifier = Modifier
            .size(24.dp)
            .clickable { onCheckedChange(!checked) },
        contentDescription = null
    )
}