package ui.component.tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.use_case.spot.model.Accessibility
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.accessibility_average
import oddspot_app.composeapp.generated.resources.accessibility_easy
import oddspot_app.composeapp.generated.resources.accessibility_hard
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
fun AccessibilityTag(modifier: Modifier = Modifier, accessibility: Accessibility) {
    Tag(modifier = modifier, text = getAccessibilityText(accessibility))
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun getAccessibilityText(accessibility: Accessibility): String {
    val res = when (accessibility) {
        Accessibility.Easy -> Res.string.accessibility_easy
        Accessibility.Average -> Res.string.accessibility_average
        Accessibility.Hard -> Res.string.accessibility_hard
    }
    return stringResource(res)
}