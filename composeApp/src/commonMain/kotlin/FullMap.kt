import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.util.CameraPosition
import ui.util.Location

@Composable
expect fun FullMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
)