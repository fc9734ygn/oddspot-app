import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.Location
import util.Event

@Composable
expect fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>? = null,
    initialCameraPosition: CameraPosition? = null,
    cameraLocationBounds: CameraLocationBounds? = null,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (Int) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int // From com.google.android.gms.maps.GoogleMaps NONE = 0, NORMAL = 1, SATELLITE = 2, TERRAIN = 3, HYBRID = 4
)