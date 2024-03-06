package ui.screen.submit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.submit_spot_accessibility_info_description_average
import oddspot_app.composeapp.generated.resources.submit_spot_accessibility_info_description_easy
import oddspot_app.composeapp.generated.resources.submit_spot_accessibility_info_description_hard
import oddspot_app.composeapp.generated.resources.submit_spot_accessibility_info_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.util.Colors
import ui.util.body
import ui.util.input

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AccessibilityInfoDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Column(
            modifier = Modifier.background(
                color = Colors.background,
                shape = RoundedCornerShape(12.dp)
            ).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(Res.string.submit_spot_accessibility_info_title),
                style = input().copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildBoldFirstWord(Res.string.submit_spot_accessibility_info_description_easy),
                style = body().copy(textAlign = TextAlign.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildBoldFirstWord(Res.string.submit_spot_accessibility_info_description_average),
                style = body().copy(textAlign = TextAlign.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildBoldFirstWord(Res.string.submit_spot_accessibility_info_description_hard),
                style = body().copy(textAlign = TextAlign.Start)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun buildBoldFirstWord(res: StringResource): AnnotatedString {
    return buildAnnotatedString {
        val string = stringResource(res)
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(string.substringBefore(" "))
        }
        append(" ")
        append(string.substringAfter(" "))
    }
}