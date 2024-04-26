package ui.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_bottom_nav_account
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.Colors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DefaultAvatar(
    size: Dp = 124.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Colors.red, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_bottom_nav_account),
            contentDescription = null,
            tint = Colors.black,
            modifier = Modifier.size(64.dp)
                .align(Alignment.Center)
        )
    }
}