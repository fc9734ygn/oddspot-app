package ui.screen.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mxalbert.zoomable.Zoomable
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.base.BaseScreen
import ui.util.Colors

class FullScreenImageScreen(private val imageUrl: String) : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Colors.background)
        ) {
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
            val navigator = LocalNavigator.currentOrThrow
            Box(modifier = Modifier.padding(24.dp)
                .size(48.dp)
                .clickable { navigator.pop() }
                .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Colors.darkGrey
                )
            }
        }
    }
}