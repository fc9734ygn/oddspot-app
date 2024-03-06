package ui.screen.submit

import FullMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.base.BaseScreen
import ui.util.CameraPosition
import ui.util.Location
import ui.util.toLatLong

private const val DEFAULT_ZOOM = 17f

class LocationPickerScreen(
    private val userLocation: Location
) : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val location = remember { mutableStateOf(userLocation) }

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
                    }
                )
                Image(
                    painter = painterResource(Res.drawable.ic_marker),
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_marker_button),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .clickable {
                            navigator.popUntil{
                                it is SubmitSpotScreen
                            }
                            navigator.pop()
                            navigator.push(
                                SubmitSpotScreen(
                                    selectedLocation = location.value
                                )
                            )
                        }
                        .size(64.dp)
                    ,
                    tint = Color.Unspecified
                )
            }
        }
    }
}