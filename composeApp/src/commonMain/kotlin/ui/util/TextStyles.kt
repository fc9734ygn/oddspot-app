package ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.fontFamilyResource

@Composable
fun h1() : TextStyle {
   return TextStyle(
       fontFamily = fontFamilyResource(MR.fonts.passionone_regular),
       fontSize = 48.sp,
       textAlign = TextAlign.Center
   )
}

@Composable
fun h3() : TextStyle {
   return TextStyle(
       fontFamily = fontFamilyResource(MR.fonts.passionone_regular),
       fontSize = 24.sp,
       textAlign = TextAlign.Center
   )
}

@Composable
fun body() : TextStyle {
   return TextStyle(
       fontFamily = fontFamilyResource(MR.fonts.inter_regular),
       fontSize = 14.sp,
       textAlign = TextAlign.Center
   )
}

@Composable
fun button() : TextStyle {
   return TextStyle(
       fontFamily = fontFamilyResource(MR.fonts.inter_semibold),
       fontSize = 24.sp,
       textAlign = TextAlign.Center,
   )
}

@Composable
fun input() : TextStyle {
    return TextStyle(
        fontFamily = fontFamilyResource(MR.fonts.inter_regular),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun footnote() : TextStyle {
   return TextStyle(
       fontFamily = fontFamilyResource(MR.fonts.inter_regular),
       fontSize = 12.sp,
       textAlign = TextAlign.Center
   )
}