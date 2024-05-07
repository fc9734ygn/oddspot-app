
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.util.Location
import ui.util.CameraPosition
import util.Event

@Composable
expect fun LocationRefinementMap(
    modifier: Modifier,
    initialCameraPosition: CameraPosition,
    onSelectionChange: (Location) -> Unit,
    event: Event<MapControlsEvent>?,
    initialMapType: Int
)