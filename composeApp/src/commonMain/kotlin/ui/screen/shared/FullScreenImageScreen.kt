package ui.screen.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mxalbert.zoomable.Zoomable
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ui.base.BaseScreen
import ui.util.Colors

class FullScreenImageScreen(private val imageUrl: String) : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        Box(modifier = Modifier.fillMaxSize().background(color = Colors.background)) {
            Zoomable {
                val painter = asyncPainterResource(imageUrl)
                KamelImage(
                    resource = painter,
                    onLoading = { progress -> CircularProgressIndicator(progress) },
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}