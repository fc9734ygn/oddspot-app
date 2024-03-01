package ui.screen.explore

import GoogleMaps
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.koin.getScreenModel
import ui.base.BaseScreen
import ui.util.CameraPosition
import ui.util.Colors
import ui.util.LatLong

class ExploreScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {

        val screenModel = getScreenModel<ExploreScreenModel>()
        val state by screenModel.state.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            val currentUserLatLong =
                state.userCurrentLocation?.let { LatLong(it.first, it.second) }
                    ?: LatLong(0.0, 0.0)

            GoogleMaps(
                modifier = Modifier.fillMaxSize(),
                markers = state.markers,
                cameraPosition = CameraPosition(currentUserLatLong, 13f),
                cameraLocationBounds = null,
                userCurrentLocation = state.userCurrentLocation,
                onPermissionsGranted = { screenModel.getCurrentUserLocation() }
            )
            Box(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Colors.black,
                            Color.Transparent
                        ),
                        endY = 300f
                    )
                )
            )
            Box(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Colors.black,
                            Color.Transparent
                        ),
                        endY = 2000f,
                        startY = Float.POSITIVE_INFINITY
                    )
                )
            )
        }
    }
}