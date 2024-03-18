package ui.screen.explore.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import domain.use_case.spot.model.ReportReason
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.img_report_dialog
import oddspot_app.composeapp.generated.resources.spot_detail_report_dialog_option_changed
import oddspot_app.composeapp.generated.resources.spot_detail_report_dialog_option_other
import oddspot_app.composeapp.generated.resources.spot_detail_report_dialog_option_unsafe
import oddspot_app.composeapp.generated.resources.spot_detail_report_dialog_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.util.Colors
import ui.util.body
import ui.util.h1

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SpotReportDialog(
    onDismissRequest: () -> Unit,
    onReportReason: (ReportReason) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.background(
                color = Colors.background,
                shape = RoundedCornerShape(12.dp)
            ).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.spot_detail_report_dialog_title),
                style = h1()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painterResource(Res.drawable.img_report_dialog),
                contentDescription = null,
                modifier = Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Colors.lightGrey)
            Text(
                text = stringResource(Res.string.spot_detail_report_dialog_option_unsafe),
                style = body(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { onReportReason(ReportReason.Unsafe) }
            )
            Divider(color = Colors.lightGrey)
            Text(
                text = stringResource(Res.string.spot_detail_report_dialog_option_changed),
                style = body(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { onReportReason(ReportReason.SpotChangedOrGone) }

            )
            Divider(color = Colors.lightGrey)
            Text(
                text = stringResource(Res.string.spot_detail_report_dialog_option_other),
                style = body(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable { onReportReason(ReportReason.Other) }
            )
        }
    }
}