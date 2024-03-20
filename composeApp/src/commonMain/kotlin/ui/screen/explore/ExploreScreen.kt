package ui.screen.explore

import ExploreMap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.launch
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_plus
import oddspot_app.composeapp.generated.resources.spot_detail_report_snackbar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseTabScreen
import ui.component.MapGradient
import ui.component.sheet.BottomSheet
import ui.component.snackbar.GenericErrorSnackbar
import ui.component.snackbar.ShowSnackBar
import ui.screen.explore.detail.SpotDetailSheet
import ui.screen.submit.SubmitSpotScreen
import ui.util.CameraPosition
import ui.util.Colors
import ui.util.Consume
import ui.util.LatLong
import ui.util.toLatLong

class ExploreScreen : BaseTabScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val screenModel = getScreenModel<ExploreScreenModel>()
        val state by screenModel.state.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val sheetState = rememberFlexibleBottomSheetState(
            isModal = true,
        )

        state.event?.Consume {
            when (it) {
                is ExploreScreenEvent.Error -> GenericErrorSnackbar(snackbarHostState)
                is ExploreScreenEvent.OpenSpotDetailBottomSheet -> {
                    scope.launch {
                        sheetState.fullyExpand()
                    }
                }

                is ExploreScreenEvent.CloseSpotDetailBottomSheet -> {
                    scope.launch {
                        sheetState.hide()
                    }
                }

                is ExploreScreenEvent.ShowReportSuccessSnackbar -> {
                    ShowSnackBar(
                        snackbarHostState,
                        message = stringResource(Res.string.spot_detail_report_snackbar)
                    )
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            ExploreMap(
                modifier = Modifier.fillMaxSize(),
                markers = state.markers,
                cameraPosition = CameraPosition(
                    state.cameraPosition?.toLatLong() ?: LatLong(
                        0.0,
                        0.0
                    ), MAP_ZOOM_DEFAULT
                ),
                userCurrentLocation = state.userCurrentLocation,
                onPermissionsGranted = { screenModel.getData() },
                onMarkerClick = screenModel::onMarkerClick
            )
            MapGradient(endY = 200f)
            FloatingActionButton(
                modifier = Modifier.padding(8.dp).align(Alignment.BottomStart),
                onClick = { navigator.push(SubmitSpotScreen()) },
                backgroundColor = Colors.red,
                contentColor = Colors.black,
            ) {
                Icon(painterResource(Res.drawable.ic_plus), contentDescription = null)
            }
            val selectedSpotId = state.spotDetailsSheetState.spotId
            if (selectedSpotId != null) {
                BottomSheet(
                    onDismissRequest = screenModel::onSpotDetailsSheetDismiss,
                    state = sheetState,
                ) {
                    SpotDetailSheet(
                        state = state.spotDetailsSheetState,
                        onWishlistClick = screenModel.onWishlistClick(selectedSpotId),
                        onReportDialogDismiss = screenModel::onReportDialogDismiss,
                        onReportClick = screenModel::onReportClick,
                        onReportReason = screenModel::onReportReason,
                    )
                }
            }
        }
    }

    companion object {
        const val MAP_ZOOM_DEFAULT = 13f
    }
}