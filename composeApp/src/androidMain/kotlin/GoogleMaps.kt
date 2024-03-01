

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.permission_fine_location_rationale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    cameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
    userCurrentLocation: Pair<Double, Double>?,
    onPermissionsGranted: () -> Unit
) {
    val context: Context = LocalContext.current

    PermissionDialog(
        permissions = mapOf(
            Pair(
                Manifest.permission.ACCESS_FINE_LOCATION,
                stringResource(Res.string.permission_fine_location_rationale)
            ),
            Pair(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                stringResource(Res.string.permission_fine_location_rationale)
            ),
        ),
        onPermissionsGranted = onPermissionsGranted
    )
    val allPermissionsGranted =
        context.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && context.hasPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    if (!allPermissionsGranted){
        return
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(cameraPosition) {
        cameraPosition?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.target.latitude,
                        it.target.longitude
                    ), it.zoom
                )
            )
        }
    }

    LaunchedEffect(cameraLocationBounds) {
        cameraLocationBounds?.let {

            val latLngBounds = LatLngBounds.builder().apply {
                it.coordinates.forEach { latLong ->
                    include(LatLng(latLong.latitude, latLong.longitude))
                }
            }.build()

            cameraPositionState.move(
                CameraUpdateFactory.newLatLngBounds(latLngBounds, it.padding)
            )
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val properties = remember {
            mutableStateOf(
                MapProperties(
                    isMyLocationEnabled = true,
                    mapType = MapType.SATELLITE
                )
            )
        }

        val uiSettings = remember {
            mutableStateOf(
                MapUiSettings(
                    myLocationButtonEnabled = true,
                    zoomControlsEnabled = false
                )
            )
        }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = properties.value,
            uiSettings = uiSettings.value,
        ) {
            markers?.forEach { marker ->
                Marker(
                    state = rememberMarkerState(
                        key = marker.id,
                        position = LatLng(marker.coordinates.first, marker.coordinates.second)
                    ),
                )
            }
        }
    }
}