package ui.screen.explore.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_heart_filled
import oddspot_app.composeapp.generated.resources.ic_heart_outlined
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.Colors
import ui.util.noRippleClickable

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SpotDetailMainImage(
    url: String,
    onImageClick: () -> Unit,
    onWishlistClick: () -> Unit,
    isWishlisted: Boolean
) {
    Box {
        KamelImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable { onImageClick() },
            resource = asyncPainterResource(url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { progress -> CircularProgressIndicator(progress) },
        )
        val icon = if (isWishlisted) {
            painterResource(Res.drawable.ic_heart_filled)
        } else {
            painterResource(Res.drawable.ic_heart_outlined)
        }
        Icon(
            icon,
            contentDescription = null,
            tint = Colors.red,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 40.dp)
                .align(Alignment.TopEnd)
                .size(32.dp)
                .noRippleClickable(onWishlistClick),
        )
    }
}