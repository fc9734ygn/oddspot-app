
import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.homato.oddspot.R
import domain.util.Location
import kotlinx.coroutines.launch
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.permission_fine_location_rationale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.screen.explore.ExploreMarker
import ui.util.CameraPosition
import ui.util.Consume
import util.Event

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    initialCameraPosition: CameraPosition?,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (Int) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
) {
    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()

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

    if (!allPermissionsGranted || initialCameraPosition == null) {
        return
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    initialCameraPosition.target.latitude,
                    initialCameraPosition.target.longitude
                ), initialCameraPosition.zoom
            )
        )
    }

    val properties = remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = initialMapType.toMapType(),
            )
        )
    }

    val uiSettings = remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false,
                tiltGesturesEnabled = false,
                mapToolbarEnabled = false
            )
        )
    }

    event?.Consume {
        when (it) {
            is MapControlsEvent.AnimateToLocation -> {
                scope.launch {
                    val currentPosition = cameraPositionState.position

                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.location.latitude,
                                it.location.longitude
                            ), currentPosition.zoom
                        )
                    )
                }
            }

            is MapControlsEvent.MapTypeChange -> {
                properties.value = properties.value.copy(mapType = it.mapType.toMapType())
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = properties.value,
            uiSettings = uiSettings.value,
            contentPadding = PaddingValues(8.dp)
        ) {
            markers?.forEach { marker ->
                Marker(
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_png),
                    state = rememberMarkerState(
                        key = marker.id.toString(),
                        position = LatLng(marker.coordinates.latitude, marker.coordinates.longitude)
                    ),
                    onClick = {
                        onMarkerClick(marker.id)
                        true
                    }
                )
            }
        }
    }
}

@Suppress("MagicNumber")
fun Int.toMapType(): MapType {
    return when (this) {
        0 -> MapType.NONE
        1 -> MapType.NORMAL
        2 -> MapType.SATELLITE
        3 -> MapType.TERRAIN
        4 -> MapType.HYBRID
        else -> MapType.NORMAL
    }
}