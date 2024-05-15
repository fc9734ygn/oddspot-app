package ui.screen.explore.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.use_case.spot.AREA_RADIUS_METERS
import domain.use_case.spot.SPOT_RADIUS_METERS
import domain.use_case.spot.model.ReportReason
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.spot_detail_button_primary_button
import oddspot_app.composeapp.generated.resources.spot_detail_range_explanation
import oddspot_app.composeapp.generated.resources.spot_detail_report
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.component.button.PrimaryButton
import ui.screen.explore.visit.VisitScreen
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SpotDetailMainImage(
                url = state.mainImage,
                onImageClick = { navigator.push(FullScreenImageScreen(state.mainImage)) },
                onWishlistClick = onWishlistClick,
                isWishlisted = state.isWishlisted
            )
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
            SpotDetailStatsRow(
                accessibility = state.accessibility,
                isArea = state.isArea,
                amountOfVisits = state.amountOfVisits,
                dislikes = state.dislikes,
                likes = state.likes
            )
            Spacer(modifier = Modifier.height(16.dp))
            SpotDetailVisitImagesRow(
                images = state.visitImages,
                onImageClick = { navigator.push(FullScreenImageScreen(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Column {
            Text(
                text = stringResource(
                    Res.string.spot_detail_range_explanation,
                    if (state.isArea) AREA_RADIUS_METERS else SPOT_RADIUS_METERS
                ),
                style = body(),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                text = stringResource(Res.string.spot_detail_button_primary_button),
                onClick = { state.spotId?.let { navigator.push(VisitScreen(state.spotId)) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                isEnabled = state.isInRange,
                showLockIcon = !state.isInRange
            )
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}