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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.base.BaseTabScreen
import ui.component.MapGradient
import ui.screen.submit.SubmitSpotScreen
import ui.util.CameraPosition
import ui.util.Colors
import ui.util.LatLong
import ui.util.toLatLong

class ExploreScreen : BaseTabScreen() {

    @OptIn(ExperimentalResourceApi::class)
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
            FloatingActionButton(
                modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
                onClick = { navigator.push(SubmitSpotScreen()) },
                backgroundColor = Colors.red,
                contentColor = Colors.black,
            ) {
                Icon(painterResource(Res.drawable.ic_plus), contentDescription = null)
            }
        }
    }

    companion object {
        const val MAP_ZOOM_DEFAULT = 13f
    }
}