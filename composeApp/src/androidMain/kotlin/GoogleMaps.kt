
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition

@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    cameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
) {

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

    GoogleMap(
        cameraPositionState = cameraPositionState,
        modifier = modifier
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