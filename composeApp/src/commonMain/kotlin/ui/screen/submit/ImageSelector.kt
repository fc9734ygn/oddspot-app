package ui.screen.submit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_edit
import oddspot_app.composeapp.generated.resources.submit_spot_upload_image_hint
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.util.Colors
import ui.util.dashedBorder
import ui.util.input

private const val RESIZE_THRESHOLD_BYTES = 15 * 1024 * 1024L // 15MB
private const val RESIZE_IMAGE_SIZE_WIDTH = 2560
private const val RESIZE_IMAGE_SIZE_HEIGHT = 1440
private const val RESIZE_IMAGE_COMPRESSION_QUALITY = 1.0

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageSelector(
    modifier: Modifier = Modifier,
    image: ByteArray?,
    onImageSelected: (ByteArray) -> Unit
) {

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onImageSelected(it)
            }
        },
        resizeOptions = ResizeOptions(
            resizeThresholdBytes = RESIZE_THRESHOLD_BYTES,
            compressionQuality = RESIZE_IMAGE_COMPRESSION_QUALITY,
            width = RESIZE_IMAGE_SIZE_WIDTH,
            height = RESIZE_IMAGE_SIZE_HEIGHT
        ),
    )

    if (image != null) {
        Box {
            Image(
                modifier = modifier
                    .aspectRatio(2f)
                    .clip(RoundedCornerShape(24.dp)),
                bitmap = image.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .clickable { singleImagePicker.launch() }
                ,
                painter = painterResource(Res.drawable.ic_edit),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    } else {
        Box(
            modifier = modifier
                .background(color = Colors.background, shape = RoundedCornerShape(24.dp))
                .dashedBorder(
                    color = Colors.red,
                    shape = RoundedCornerShape(24.dp),
                    strokeWidth = 2.dp,
                    gapWidth = 2.5.dp,
                    cap = StrokeCap.Butt
                )
                .aspectRatio(2f)
                .clickable { singleImagePicker.launch() },
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(Res.string.submit_spot_upload_image_hint),
                style = input().copy(textDecoration = TextDecoration.Underline)
            )
        }
    }
}