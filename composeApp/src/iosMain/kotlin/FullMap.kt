import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.animateToCameraPosition
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.darwin.NSObject
import ui.util.CameraPosition
import domain.util.Location
import kotlinx.coroutines.DefaultExecutor.delegate
import util.Event

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LocationRefinementMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
) {
    val mapViewDelegate = remember {
        FullMapViewDelegate(onCameraIdle = onSelectionChange)
    }

    val mapView = remember {
        GMSMapView().apply {
            delegate = mapViewDelegate
            camera = GMSCameraPosition.cameraWithTarget(
                CLLocationCoordinate2DMake(
                    initialCameraPosition.target.latitude,
                    initialCameraPosition.target.longitude
                ),
                initialCameraPosition.zoom.toFloat()
            )
            setMyLocationEnabled(true)
            setMapType(initialMapType.toULong())
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
            event?.get()?.let {
                when (it) {
                    is MapControlsEvent.AnimateToLocation -> {
                        view.animateToCameraPosition(
                            GMSCameraPosition.cameraWithLatitude(
                                it.location.latitude,
                                it.location.longitude,
                                initialCameraPosition.zoom
                            )
                        )
                    }

                    is MapControlsEvent.MapTypeChange -> {
                        mapView.apply {
                            setMapType(it.mapType.toULong())
                        }
                    }
                }
            }
        }
    )
}

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@ExportObjCClass
class FullMapViewDelegate(private val onCameraIdle: (Location) -> Unit) : NSObject(),
    GMSMapViewDelegateProtocol {
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


