package ui.screen.policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import ui.util.body
import ui.util.h1
import ui.util.h3

class PolicyScreen : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .background(color = colorResource(MR.colors.background))
                .padding(vertical = 64.dp, horizontal = 48.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(MR.strings.privacy_policy_and_terms_title),
                style = h1(),
                color = colorResource(MR.colors.white)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                style = h3(),
                color = colorResource(MR.colors.white),
                text = stringResource(MR.strings.privacy_policy_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur Aliquam semper, tortor id dignissim posuere, ipsum justo malesuada arcu, vel porttitor lectus nisi sollicitudin mi. Suspendisse lorem libero, fermentum quis elit a, vehicula commodo urna. Duis viverra blandit facilisis. Pellentesque vitae pharetra nulla. Integer tristique, nisl non rutrum gravida, enim ligula placerat lectus, et bibendum nibh elit in nisi. Vestibulum nec placerat lectus. Nulla pretium purus id risus porttitor faucibus. Maecenas pretium erat lorem, eu tincidunt massa molestie et. Phasellus sed consequat nunc. Pellentesque accumsan luctus elit et tempus. adipiscing elit. Proin nunc sapien, tincidunt vitae quam quis, rutrum tristique risus. Quisque non dignissim leo. Nam fringilla elit eu vulputate mattis. Nullam viverra tincidunt dui, ultrices rutrum turpis malesuada at. Aliquam et pellentesque dolor. Phasellus iaculis quam est, vel ultrices nulla tincidunt ac. In at sapien feugiat neque dapibus feugiat et ac urna. Fusce eget elit nunc. Cras eu tortor quis enim viverra efficitur.",
                color = colorResource(MR.colors.white),
                style = body().copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                style = h3(),
                color = colorResource(MR.colors.white),
                text = stringResource(MR.strings.terms_of_service_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sit amet nisi eget felis ornare egestas non porta ante. Aenean vitae aliquam massa. Sed aliquam quam id justo semper consectetur. Vestibulum porta ipsum id odio auctor suscipit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Morbi blandit sem non lorem vehicula placerat. Vivamus dictum porttitor dui, in consequat purus eleifend at. Donec magna massa, vehicula at odio eget, convallis rhoncus odio. Etiam porttitor nunc ligula, non vehicula enim luctus a. Donec quis lorem arcu. Sed sed ultricies purus. Maecenas malesuada vel nulla sit Ut at pulvinar enim. Donec ut massa eget enim commodo tristique sit amet vitae turpis. Sed sodales pulvinar cursus. Donec posuere justo et consequat eleifend. Nulla nibh quam, pellentesque eu dui nec, condimentum fermentum ante. Pellentesque eleifend, mi non posuere suscipit, arcu mi vestibulum velit, vitae rhoncus nunc purus id quam. Integer egestas blandit finibus. Proin interdum, justo in cursus pellentesque, massa erat suscipit mi, a vulputate metus libero eu elit. amet elementum. Pellentesque dictum turpis ut urna ultrices porta. Etiam fringilla nec quam ac sodales. Proin nunc sapien, tincidunt vitae quam quis, rutrum tristique risus. Quisque non dignissim leo. Nam fringilla elit eu vulputate mattis. Nullam viverra tincidunt dui, ultrices rutrum turpis malesuada at. Aliquam et pellentesque dolor. Phasellus iaculis quam est, vel ultrices nulla tincidunt ac. In at sapien feugiat neque dapibus feugiat et ac urna. Fusce eget elit nunc. Cras eu tortor quis enim viverra efficitur.",
                color = colorResource(MR.colors.white),
                style = body().copy(textAlign = TextAlign.Center)
            )
        }
    }

}