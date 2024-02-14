package ui.component.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun PrimaryCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Image(
        painter = painterResource(
            if (checked) MR.images.ic_checkbox_checked
            else MR.images.ic_checkbox_unchecked
        ),
        modifier = Modifier
            .size(24.dp)
            .clickable { onCheckedChange(!checked) },
        contentDescription = null
    )
}