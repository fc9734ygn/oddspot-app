package ui.screen.submit.location_picker

import FullMap
import MapControlsEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_marker
import oddspot_app.composeapp.generated.resources.ic_marker_button
import oddspot_app.composeapp.generated.resources.ic_my_location
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.base.BaseScreen
import ui.screen.explore.OddSpotMapType
import ui.screen.submit.SubmitSpotScreen
import ui.util.CameraPosition
import ui.util.Colors
import ui.util.Location
import ui.util.toLatLong
import util.Event

private const val DEFAULT_ZOOM = 17f

class LocationPickerScreen(
    private val userLocation: Location
) : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val location = remember { mutableStateOf(userLocation) }
        val event = remember { mutableStateOf<Event<MapControlsEvent>?>(null) }

        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                FullMap(
                    modifier = Modifier.fillMaxSize(),
                    initialCameraPosition = CameraPosition(
                        userLocation.toLatLong(),
                        DEFAULT_ZOOM
                    ),
                    onSelectionChange = { updatedLocation ->
                        location.value = updatedLocation
                    },
                    event = event.value,
                    initialMapType = OddSpotMapType.HYBRID.value,
                )
                Image(
                    painter = painterResource(Res.drawable.ic_marker),
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                ) {
                    FloatingActionButton(
                        onClick = {
                            navigator.popUntil {
                                it is SubmitSpotScreen
                            }
                            navigator.pop()
                            navigator.push(
                                SubmitSpotScreen(
                                    selectedLocation = location.value
                                )
                            )
                        },
                        backgroundColor = Colors.red,
                        contentColor = Colors.black,
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_marker_button),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FloatingActionButton(
                        onClick = {
                            event.value = Event(MapControlsEvent.AnimateToLocation(userLocation))
                        },
                        backgroundColor = Colors.lightGrey,
                        contentColor = Colors.black,
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_my_location),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}