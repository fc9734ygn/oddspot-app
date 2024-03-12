package ui.screen.explore

import ExploreMap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.base.BaseTabScreen
import ui.component.MapGradient
import ui.component.button.PrimaryButton
import ui.screen.submit.SubmitSpotScreen
import ui.util.CameraPosition
import ui.util.LatLong
import ui.util.toLatLong

class ExploreScreen : BaseTabScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {

        val screenModel = getScreenModel<ExploreScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize()) {
            val currentUserLatLong =
                state.userCurrentLocation?.toLatLong()
                    ?: LatLong(0.0, 0.0)

            ExploreMap(
                modifier = Modifier.fillMaxSize(),
                markers = state.markers,
                cameraPosition = CameraPosition(currentUserLatLong, MAP_ZOOM_DEFAULT),
                userCurrentLocation = state.userCurrentLocation,
                onPermissionsGranted = { screenModel.getCurrentUserLocation() }
            )
            MapGradient(
                endY = 200f
            )
            MapGradient(
                startY = 2600f,
                endY = 2800f,
                reverse = true
            )
            PrimaryButton(
                onClick = { navigator.push(SubmitSpotScreen()) },
                text = "Submit spot"
            )
        }
    }

    companion object {
        const val MAP_ZOOM_DEFAULT = 13f
    }
}