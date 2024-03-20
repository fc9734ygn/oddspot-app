package ui.screen.account.visited

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.use_case.spot.model.VisitedSpotItemModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ui.component.tag.AccessibilityTag
import ui.util.body

@Composable
fun VisitedSpotItem(state: VisitedSpotItemModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        KamelImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp)),
            resource = asyncPainterResource(state.mainImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { progress -> CircularProgressIndicator(progress) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = state.title,
            style = body(),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        AccessibilityTag(modifier = Modifier.padding(horizontal = 24.dp), state.accessibility)
        Spacer(modifier = Modifier.height(16.dp))
    }
}