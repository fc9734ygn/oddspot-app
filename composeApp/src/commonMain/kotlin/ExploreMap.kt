
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.Location

@Composable
expect fun ExploreMap(
    modifier: Modifier,
    markers: List<ExploreMarker>? = null,
    cameraPosition: CameraPosition? = null,
    cameraLocationBounds: CameraLocationBounds? = null,
    userCurrentLocation: Location?,
    onPermissionsGranted: () -> Unit,
    onMarkerClick: (String) -> Unit,
)