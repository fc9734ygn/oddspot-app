
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.util.Location
import ui.screen.explore.ExploreMarker
import ui.util.CameraPosition
import util.Event

@Composable
expect fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>? = null,
    initialCameraPosition: CameraPosition? = null,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (Int) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int // NONE = 0, NORMAL = 1, SATELLITE = 2, TERRAIN = 3, HYBRID = 4
)