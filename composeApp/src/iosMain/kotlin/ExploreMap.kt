import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCameraUpdate.Companion.fitBounds
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.animateWithCameraUpdate
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.darwin.NSObject
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.Location

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    cameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        onPermissionsGranted()
    }

    val mapViewDelegate = remember {
        ExploreMapViewDelegate(onMarkerClick)
    }

    val mapsView = remember {
        GMSMapView().apply {
            delegate = mapViewDelegate
            setMyLocationEnabled(true)
            settings.myLocationButton = true
            settings.setMyLocationButton(true)
            settings.setScrollGestures(true)
            settings.setZoomGestures(true)
            settings.setCompassButton(true)
            setMapType(2u) //kGMSTypeSatellite = 2
        }
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
                    userData = marker.id
                }
            }
        }
    )
}

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@ExportObjCClass
class ExploreMapViewDelegate(private val onMarkerClick: (String) -> Unit) : NSObject(),
    GMSMapViewDelegateProtocol {
    @OptIn(ExperimentalForeignApi::class)
    override fun mapView(
        mapView: GMSMapView,
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        didTapMarker: GMSMarker
    ) : Boolean // Shows error but builds ¯\_(ツ)_/¯
    {
        val userData = didTapMarker.userData()
        onMarkerClick(userData as String)
        return true
    }
}