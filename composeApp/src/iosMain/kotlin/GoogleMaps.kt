import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCameraUpdate.Companion.fitBounds
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.animateWithCameraUpdate
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    cameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
    userCurrentLocation: Pair<Double, Double>?,
    onPermissionsGranted: () -> Unit
) {
    val mapsView = remember {
        GMSMapView()
    }

    UIKitView(
        modifier = modifier.fillMaxSize(),
        interactive = true,
        factory = {
            mapsView
        },
        update = { view ->
            cameraPosition?.let {
                view.setCamera(
                    GMSCameraPosition.cameraWithLatitude(
                        it.target.latitude,
                        it.target.longitude,
                        it.zoom
                    )
                )
            }

            cameraLocationBounds?.let {

                val bounds = GMSCoordinateBounds()
                it.coordinates.forEach {
                    bounds.includingCoordinate(
                        CLLocationCoordinate2DMake(
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    )
                }
                GMSCameraUpdate().apply {
                    fitBounds(bounds, it.padding.toDouble())
                    view.animateWithCameraUpdate(this)
                }
            }

            markers?.forEach { marker ->
                GMSMarker().apply {
                    position = CLLocationCoordinate2DMake(
                        marker.coordinates.first,
                        marker.coordinates.second
                    )
                    map = view
                }
            }
        }
    )
}