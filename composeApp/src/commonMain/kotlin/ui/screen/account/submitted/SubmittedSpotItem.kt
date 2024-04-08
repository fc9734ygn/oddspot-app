package ui.screen.account.submitted

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.use_case.spot.model.SubmissionStatus
import domain.use_case.spot.model.SubmittedSpotItemModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_info
import oddspot_app.composeapp.generated.resources.submitted_spots_rejected
import oddspot_app.composeapp.generated.resources.submitted_spots_submitted
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.component.tag.AccessibilityTag
import ui.util.Colors
import ui.util.DateFormatter
import ui.util.body

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SubmittedSpotItem(state: SubmittedSpotItemModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box {
            KamelImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(24.dp)),
                resource = asyncPainterResource(state.mainImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress -> CircularProgressIndicator(progress) }
            )
            if (state.status != SubmissionStatus.Verified) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .padding(horizontal = 24.dp)
                        .background(
                            color = Colors.white.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (state.status) {
                                SubmissionStatus.Rejected -> stringResource(Res.string.submitted_spots_rejected)
                                SubmissionStatus.Submitted -> stringResource(Res.string.submitted_spots_submitted)
                                else -> ""
                            },
                            style = body(),
                            textAlign = TextAlign.Center,
                            color = when (state.status) {
                                SubmissionStatus.Rejected -> Colors.red
                                SubmissionStatus.Submitted -> Colors.black
                                else -> Colors.black
                            },
                        )
                        if (state.status == SubmissionStatus.Rejected) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(Res.drawable.ic_info),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Colors.red
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.title,
                style = body(),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = DateFormatter.formatTimestamp(state.submissionTimestamp),
                style = body(),
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AccessibilityTag(modifier = Modifier.padding(horizontal = 24.dp), state.accessibility)
        Spacer(modifier = Modifier.height(16.dp))
    }
}