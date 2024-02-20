import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.LatLong

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    cameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
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
                        it.latLong.latitude,
                        it.latLong.longitude,
                        it.zoom
                    )
                )
            }

            cameraPositionLatLongBounds?.let {

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
                        marker.position.latitude,
                        marker.position.longitude
                    )
                    title = marker.title
                    map = view
                }
            }

            polyLine?.let { polyLine ->
                val points = polyLine.map {
                    CLLocationCoordinate2DMake(it.latitude, it.longitude)
                }
                val path = GMSMutablePath().apply {
                    points.forEach { point ->
                        addCoordinate(point)
                    }
                }

                GMSPolyline().apply {
                    this.path = path
                    this.map = view
                }
            }
        }
    )
}