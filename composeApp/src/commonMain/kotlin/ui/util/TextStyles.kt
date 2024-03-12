@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import oddspot_app.composeapp.generated.resources.Inter_Medium
import oddspot_app.composeapp.generated.resources.Inter_Regular
import oddspot_app.composeapp.generated.resources.PassionOne_Regular
import oddspot_app.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@Composable
fun h1() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
       fontSize = 48.sp,
       textAlign = TextAlign.Center,
       color = Colors.white,
       lineHeight = 45.sp
   )
}

@Composable
fun h3() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
       fontSize = 24.sp,
       textAlign = TextAlign.Center,
       color = Colors.white
   )
}

@Composable
fun body() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
       fontSize = 14.sp,
       textAlign = TextAlign.Center,
       color = Colors.white
   )
}

@Composable
fun button() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.Inter_Medium)),
       fontSize = 24.sp,
       textAlign = TextAlign.Center,
   )
}

@Composable
fun input() : TextStyle {
    return TextStyle(
        fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        color = Colors.white
    )
}

@Composable
fun footnote() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
       fontSize = 12.sp,
       textAlign = TextAlign.Center,
       color = Colors.white
   )
}