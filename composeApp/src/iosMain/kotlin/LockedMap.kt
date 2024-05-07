
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import kotlinx.cinterop.ExperimentalForeignApi
import ui.util.CameraPosition

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LockedMap(
    modifier: Modifier,
    cameraPosition: CameraPosition,
    mapType: Int
) {
    val mapView = remember {
        GMSMapView().apply {
            setMyLocationEnabled(true)
            settings.setAllGesturesEnabled(false)
            settings.myLocationButton = false
            setMapType(mapType.toULong())
        }
    }

    UIKitView(
        modifier = modifier,
        factory = { mapView },
        update = {view ->
            cameraPosition.let {
                view.setCamera(
                    GMSCameraPosition.cameraWithLatitude(
                        it.target.latitude,
                        it.target.longitude,
                        it.zoom
                    )
                )
            }
        }
    )
}
