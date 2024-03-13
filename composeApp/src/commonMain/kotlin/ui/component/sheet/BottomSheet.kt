package ui.component.sheet

import androidx.compose.runtime.Composable
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetState
import ui.util.Colors

@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    state: FlexibleSheetState,
    content: @Composable () -> Unit,
) {
    FlexibleBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = state,
        containerColor = Colors.background,
    ) {
        content()
    }
}