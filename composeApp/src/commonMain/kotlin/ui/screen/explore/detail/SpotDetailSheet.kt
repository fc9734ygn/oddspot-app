package ui.screen.explore.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.use_case.spot.model.ReportReason
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_visit
import oddspot_app.composeapp.generated.resources.spot_detail_button_mark_visited
import oddspot_app.composeapp.generated.resources.spot_detail_report
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.component.button.PrimaryButton
import ui.component.tag.AccessibilityTag
import ui.screen.shared.FullScreenImageScreen
import ui.util.Colors
import ui.util.body
import ui.util.footnote
import ui.util.h3

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SpotDetailSheet(
    state: SpotDetailSheetState,
    onWishlistClick: () -> Unit,
    onReportDialogDismiss: () -> Unit,
    onReportClick: () -> Unit,
    onReportReason: (ReportReason) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    if (state.showReportDialog) {
        SpotReportDialog(
            onDismissRequest = onReportDialogDismiss,
            onReportReason = onReportReason,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            KamelImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { navigator.push(FullScreenImageScreen(state.mainImage)) },
                resource = asyncPainterResource(state.mainImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator(progress) },
            )
            val icon = if (state.isWishlisted) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.Favorite
            }
            Icon(
                icon,
                contentDescription = null,
                tint = Colors.red,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 40.dp)
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .clickable { onWishlistClick() },
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = state.title,
            style = h3(),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.description,
            style = body(),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Row {
                Icon(
                    painterResource(Res.drawable.ic_visit),
                    contentDescription = null,
                    tint = Colors.lightGrey
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = state.amountOfVisits.toString(), style = body())
            }
            AccessibilityTag(accessibility = state.accessibility)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            items(state.visitImages.size) { index ->
                KamelImage(
                    modifier = Modifier
                        .height(124.dp)
                        .width(184.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            navigator.push(FullScreenImageScreen(state.visitImages[index]))
                        },
                    resource = asyncPainterResource(state.visitImages[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onLoading = { progress -> CircularProgressIndicator(progress) },
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.spot_detail_report),
            style = footnote(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable { onReportClick() },
            textAlign = TextAlign.Start,
            textDecoration = TextDecoration.Underline,
            color = Colors.darkGrey
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = stringResource(Res.string.spot_detail_button_mark_visited),
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp)
        )
    }
}