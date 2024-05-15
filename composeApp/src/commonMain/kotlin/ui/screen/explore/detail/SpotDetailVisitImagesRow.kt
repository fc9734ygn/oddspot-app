package ui.screen.explore.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun SpotDetailVisitImagesRow(images: List<String>, onImageClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        items(images.size) { index ->
            val imageUrl = images[index]
            KamelImage(
                modifier = Modifier
                    .height(124.dp)
                    .width(184.dp)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        onImageClick(imageUrl)
                    },
                resource = asyncPainterResource(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator(progress) },
            )
        }
    }
}