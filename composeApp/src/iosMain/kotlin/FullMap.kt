
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.darwin.NSObject
import ui.util.CameraPosition
import ui.util.Location

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun FullMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
) {
    val mapViewDelegate = remember {
        FullMapViewDelegate(onCameraIdle = onSelectionChange)
    }

    val mapView = remember {
        GMSMapView().apply {
            delegate = mapViewDelegate
            camera = GMSCameraPosition.cameraWithTarget(
                CLLocationCoordinate2DMake(initialCameraPosition.target.latitude, initialCameraPosition.target.longitude),
                initialCameraPosition.zoom.toFloat()
            )
            setMyLocationEnabled(true)
            setMapType(2u) //kGMSTypeSatellite = 2
        }
    }

    UIKitView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            initialCameraPosition.let {
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

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@ExportObjCClass
class FullMapViewDelegate(private val onCameraIdle: (Location) -> Unit) : NSObject(), GMSMapViewDelegateProtocol {
    @OptIn(ExperimentalForeignApi::class)
    override fun mapView(
        mapView: GMSMapView,
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        idleAtCameraPosition: GMSCameraPosition
    ) {
        idleAtCameraPosition.target.useContents {
            onCameraIdle(Location(this.latitude, this.longitude))
        }
    }
}

