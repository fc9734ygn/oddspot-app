package ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_edit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.component.profile.DefaultAvatar
import ui.util.Colors
import ui.util.noRippleClickable

private const val AVATAR_RESIZE_THRESHOLD_BYTES = 1 * 1024 * 1024L // 1MB
private const val AVATAR_RESIZE_IMAGE_SIZE_WIDTH = 1920
private const val AVATAR_RESIZE_IMAGE_SIZE_HEIGHT = 1080
private const val AVATAR_RESIZE_IMAGE_COMPRESSION_QUALITY = 1.0

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AvatarComponent(
    avatarUrl: String?,
    onImageSelected: (ByteArray) -> Unit,
    isAvatarLoading: Boolean
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
            resizeThresholdBytes = AVATAR_RESIZE_THRESHOLD_BYTES,
            compressionQuality = AVATAR_RESIZE_IMAGE_COMPRESSION_QUALITY,
            width = AVATAR_RESIZE_IMAGE_SIZE_WIDTH,
            height = AVATAR_RESIZE_IMAGE_SIZE_HEIGHT
        ),
    )
    Box() {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            when {
                isAvatarLoading -> {
                    Box(
                        modifier = Modifier
                            .size(124.dp)
                            .background(Colors.background, shape = CircleShape)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                avatarUrl == null -> {
                    DefaultAvatar()
                }

                else -> {
                    KamelImage(
                        modifier = Modifier
                            .size(124.dp)
                            .clip(CircleShape),
                        resource = asyncPainterResource(avatarUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        onLoading = { progress -> CircularProgressIndicator(progress) },
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .noRippleClickable { singleImagePicker.launch() },
            painter = painterResource(Res.drawable.ic_edit),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}