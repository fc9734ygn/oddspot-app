
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import ui.util.CameraPosition
import ui.util.Location
import util.Event

@Composable
actual fun FullMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
) {

    val properties = remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true, mapType = MapType.SATELLITE))
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(initialCameraPosition) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    initialCameraPosition.target.latitude,
                    initialCameraPosition.target.longitude
                ), initialCameraPosition.zoom
            )
        )
    }

    LaunchedEffect(cameraPositionState.position.target) {
        val target = cameraPositionState.position.target
        onSelectionChange(Location(target.latitude, target.longitude))
    }

    val uiSettings = remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false,
                compassEnabled = true,
                mapToolbarEnabled = false,
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true,
                tiltGesturesEnabled = false,
                zoomGesturesEnabled = true
            )
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = properties.value,
        uiSettings = uiSettings.value,
        modifier = modifier
    )
}