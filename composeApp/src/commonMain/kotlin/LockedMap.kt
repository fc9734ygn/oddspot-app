import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.screen.explore.OddSpotMapType
import ui.util.CameraPosition

@Composable
expect fun LockedMap(
    modifier: Modifier,
    cameraPosition: CameraPosition,
    mapType: Int = OddSpotMapType.HYBRID.value
)