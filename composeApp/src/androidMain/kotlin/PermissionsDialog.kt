
import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import ui.component.SimpleDialog

@Composable
fun PermissionDialog(
    permissions: Map<String, String>,
) {
    val screenModel = remember { PermissionViewModel() }
    val context: Context = LocalContext.current

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            screenModel.onPermissionResult(
                permission = permissions.keys.first(),
                isGranted = isGranted
            )
        }
    )
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            results.forEach { (permission, isGranted) ->
                screenModel.onPermissionResult(permission = permission, isGranted = isGranted)
            }
        }
    )
    if (permissions.size > 1) {
        LaunchedEffect(Unit) {
            multiplePermissionResultLauncher.launch(permissions.keys.toTypedArray())
        }
    } else {
        LaunchedEffect(Unit) {
            permissionResultLauncher.launch(permissions.keys.first())
        }
    }

    screenModel.visiblePermissionDialogQueue.forEach { permission ->
        val isPermanentlyDenied = !shouldShowRequestPermissionRationale(
            context as Activity,
            permission
        )
        SimpleDialog(
            title = "title",
            subtitle = permissions[permission],
            confirmButtonText = if (isPermanentlyDenied) {
                "allow"
            } else {
                "ok"
            },
            onDismiss = screenModel::dismissDialog,
            onConfirm = {
                if (isPermanentlyDenied) {
                    context.openAppSystemSettings()
                } else {
                    screenModel.dismissDialog()
                    if (permissions.size > 1) multiplePermissionResultLauncher.launch(
                        permissions.keys.toTypedArray()
                    ) else permissionResultLauncher.launch(
                        permission
                    )
                }
            }
        )
    }
}