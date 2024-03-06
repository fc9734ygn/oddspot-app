import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.util.CameraPosition

@Composable
expect fun LockedMap(
    modifier: Modifier,
    cameraPosition: CameraPosition,
)