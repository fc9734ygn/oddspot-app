@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)
@file:Suppress("TooManyFunctions")

package ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import oddspot_app.composeapp.generated.resources.Inter_Medium
import oddspot_app.composeapp.generated.resources.Inter_Regular
import oddspot_app.composeapp.generated.resources.Inter_SemiBold
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
       fontFamily = FontFamily(Font(Res.font.Inter_SemiBold)),
       fontSize = 26.sp,
       textAlign = TextAlign.Center,
       lineHeight = 24.sp,
       color = Colors.white,
       fontWeight = FontWeight(700)
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
       lineHeight = 29.sp
   )
}

@Composable
fun button1() : TextStyle {
   return TextStyle(
       fontFamily = FontFamily(Font(Res.font.Inter_Medium)),
       fontSize = 20.sp,
       textAlign = TextAlign.Center,
       color = Colors.white,
       lineHeight = 24.sp
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

@Composable
fun h2(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
    fontSize = 36.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    lineHeight = 34.sp
)

@Composable
fun h4(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
    fontSize = 22.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    lineHeight = 20.sp
)

@Composable
fun h5(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
    fontSize = 18.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    lineHeight = 17.sp
)

@Composable
fun h6(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.PassionOne_Regular)),
    fontSize = 16.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    lineHeight = 15.sp
)

@Composable
fun code(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Medium)),
    fontSize = 14.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    background = Colors.background
)

@Composable
fun quote(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
    fontSize = 16.sp,
    textAlign = TextAlign.Center,
    color = Colors.white,
    lineHeight = 24.sp
)

@Composable
fun paragraph(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
    fontSize = 14.sp,
    textAlign = TextAlign.Start, // Paragraphs typically start-aligned
    color = Colors.white
)

@Composable
fun ordered(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
    fontSize = 14.sp,
    textAlign = TextAlign.Start,
    color = Colors.white
)

@Composable
fun bullet(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
    fontSize = 14.sp,
    textAlign = TextAlign.Start,
    color = Colors.white
)

@Composable
fun list(): TextStyle = TextStyle(
    fontFamily = FontFamily(Font(Res.font.Inter_Regular)),
    fontSize = 14.sp,
    textAlign = TextAlign.Start,
    color = Colors.white
)