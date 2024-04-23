package ui.component.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.spot_detail_tag_area
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AreaTag(modifier: Modifier = Modifier) {
    Tag(modifier = modifier, text = stringResource(Res.string.spot_detail_tag_area))
}
