package ui.screen.account.wishlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.use_case.spot.model.WishlistItemModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ui.component.tag.AccessibilityTag
import ui.util.Colors
import ui.util.body

private const val IMAGE_ASPECT_RATIO = 1.15f

@Composable
fun WishlistItem(
    state: WishlistItemModel,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { onClick() }
    ) {
        Box {
            KamelImage(
                modifier = Modifier
                    .aspectRatio(IMAGE_ASPECT_RATIO)
                    .clip(RoundedCornerShape(24.dp)),
                resource = asyncPainterResource(state.mainImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator(progress) },
            )
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = Colors.red,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .clickable { onRemoveClick() },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = state.title,
            style = body(),
            textAlign = TextAlign.Start,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AccessibilityTag(accessibility = state.accessibility)
        Spacer(modifier = Modifier.height(16.dp))
    }
}