
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSMapView
import kotlinx.cinterop.ExperimentalForeignApi
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
    UIKitView(
        factory = {
            val mapView =  GMSMapView()
            mapView.settings.zoomGestures = true
            mapView.settings.consumesGesturesInView = true
            mapView
        },
        modifier = modifier,
        onRelease = {
            it.removeFromSuperview()
        }
    )
//    val mapsView = remember {
//        GMSMapView().apply {
//            setMyLocationEnabled(true)
//            settings.myLocationButton = true
//            settings.setMyLocationButton(true)
//            settings.setScrollGestures(true)
//            settings.setZoomGestures(true)
//            settings.setCompassButton(true)
//        }
//    }
//
//    UIKitView(
//        modifier = modifier.fillMaxSize(),
//        interactive = true,
//        factory = {
//            mapsView
//        },
//        update = { view ->
//            cameraPosition?.let {
//                view.setCamera(
//                    GMSCameraPosition.cameraWithLatitude(
//                        it.target.latitude,
//                        it.target.longitude,
//                        it.zoom
//                    )
//                )
//            }
//
//            cameraLocationBounds?.let {
//
//                val bounds = GMSCoordinateBounds()
//                it.coordinates.forEach {
//                    bounds.includingCoordinate(
//                        CLLocationCoordinate2DMake(
//                            latitude = it.latitude,
//                            longitude = it.longitude
//                        )
//                    )
//                }
//                GMSCameraUpdate().apply {
//                    fitBounds(bounds, it.padding.toDouble())
//                    view.animateWithCameraUpdate(this)
//                }
//            }
//
//            markers?.forEach { marker ->
//                GMSMarker().apply {
//                    position = CLLocationCoordinate2DMake(
//                        marker.coordinates.first,
//                        marker.coordinates.second
//                    )
//                    map = view
//                }
//            }
//        }
//    )
}