import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.screen.explore.ExploreMarker
import ui.util.CameraLocationBounds
import ui.util.CameraPosition
import ui.util.LatLong

@Composable
expect fun GoogleMaps(
    modifier: Modifier,
    markers: List<ExploreMarker>? = null,
    cameraPosition: CameraPosition? = null,
    cameraLocationBounds: CameraLocationBounds? = null,
)