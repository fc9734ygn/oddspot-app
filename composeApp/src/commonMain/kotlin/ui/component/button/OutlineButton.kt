package ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import ui.util.input

@Composable
fun OutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.height(56.dp).fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = colorResource(MR.colors.white),
        ),
        border = ButtonDefaults.outlinedBorder.copy(
            width = 2.dp,
            brush = SolidColor(colorResource(MR.colors.red))
        ),
        shape = RoundedCornerShape(32.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 4.dp),
            style = input(),
            color = colorResource(MR.colors.white)
        )
    }
}