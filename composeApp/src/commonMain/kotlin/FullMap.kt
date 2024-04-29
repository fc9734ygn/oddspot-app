import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.util.CameraPosition
import domain.util.Location
import util.Event

@Composable
expect fun FullMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
)