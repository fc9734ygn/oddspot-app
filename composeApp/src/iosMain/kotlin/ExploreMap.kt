import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.DefaultExecutor.delegate
import org.intellij.markdown.html.entities.Entities.map
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIImage
import platform.darwin.NSObject
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.Location
import util.Event

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>?,
    initialCameraPosition: CameraPosition?,
    cameraLocationBounds: CameraLocationBounds?,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (Int) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
) {
    val scope = rememberCoroutineScope()

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
            settings.setScrollGestures(true)
            settings.setZoomGestures(true)
            settings.setCompassButton(true)
            setMapType(initialMapType.toUInt())
        }
    }

    UIKitView(
        modifier = modifier.fillMaxSize(),
        interactive = true,
        factory = {
            mapsView
        },
        update = { view ->
            initialCameraPosition?.let {
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

            event?.get()?.let {
                when (it) {
                    is MapControlsEvent.AnimateToLocation -> {
//                        val cameraUpdate = GMSCameraUpdate.setTarget(CLLocationCoordinate2D(latitude = it.location.latitude, longitude = it.location.longitude), zoom = 15f) // Assuming a zoom level of 15f, adjust as needed
//                        mapsView.animateWithCameraUpdate(cameraUpdate)
                    }
                    is MapControlsEvent.MapTypeChange -> {
//                        mapsView.mapType = it.mapType.toUInt()
//                        mapsView.value.apply {
//                            setMapType(it.mapType.toUInt())
//                        }
//                        GMSMapView().apply {
//                            setMapType(it.mapType.toUInt())
//                        }
                    }
                }
            }

            markers?.forEach { marker ->
                GMSMarker().apply {
                    position = CLLocationCoordinate2DMake(
                        marker.coordinates.latitude,
                        marker.coordinates.longitude
                    )
                    icon = UIImage.imageNamed("ic_marker_png.png")
                    map = view
                    userData = marker.id
                }
            }
        }
    )
}

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
@ExportObjCClass
class ExploreMapViewDelegate(private val onMarkerClick: (Int) -> Unit) : NSObject(),
    GMSMapViewDelegateProtocol {
    @OptIn(ExperimentalForeignApi::class)
    override fun mapView(
        mapView: GMSMapView,
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        didTapMarker: GMSMarker
    ) : Boolean // Shows error but builds ¯\_(ツ)_/¯
    {
        val userData = didTapMarker.userData()
        onMarkerClick(userData as Int)
        return true
    }
}