package ui.screen.explore.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.use_case.spot.model.Accessibility
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_dislike
import oddspot_app.composeapp.generated.resources.ic_like
import oddspot_app.composeapp.generated.resources.ic_visit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.component.tag.AccessibilityTag
import ui.component.tag.AreaTag
import ui.util.Colors
import ui.util.body

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SpotDetailStatsRow(
    accessibility: Accessibility,
    isArea: Boolean,
    amountOfVisits: Int,
    dislikes: Int,
    likes: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccessibilityTag(accessibility = accessibility)
        Spacer(modifier = Modifier.width(8.dp))

        if (isArea) AreaTag()
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painterResource(Res.drawable.ic_visit),
            contentDescription = null,
            tint = Colors.lightGrey,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = amountOfVisits.toString(), style = body())
        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painterResource(Res.drawable.ic_like),
            contentDescription = null,
            tint = Colors.lightGrey,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = likes.toString(), style = body())
        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painterResource(Res.drawable.ic_dislike),
            contentDescription = null,
            tint = Colors.lightGrey,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = dislikes.toString(), style = body())

    }
}