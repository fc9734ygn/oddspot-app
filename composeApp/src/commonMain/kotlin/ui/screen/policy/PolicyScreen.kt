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
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.privacy_policy_and_terms_title
import oddspot_app.composeapp.generated.resources.privacy_policy_title
import oddspot_app.composeapp.generated.resources.terms_of_service_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.util.Colors
import ui.util.body
import ui.util.h1
import ui.util.h3

class PolicyScreen : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .background(color = Colors.background)
                .padding(vertical = 64.dp, horizontal = 48.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.privacy_policy_and_terms_title),
                style = h1(),
                color = Colors.white
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                style = h3(),
                color = Colors.white,
                text = stringResource(Res.string.privacy_policy_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur Aliqu" +
                        "am semper, tortor id dignissim posuere, i" +
                        "psum justo malesuada arcu, vel porttitor lec" +
                        "tus nisi sollicitudin mi. Suspendisse lorem l" +
                        "ibero, fermentum quis elit a, vehicula commod" +
                        "o urna. Duis viverra blandit facilisis. Pellent" +
                        "esque vitae pharetra nulla. Integer tristique, n" +
                        "isl non rutrum gravida, enim ligula placerat lect" +
                        "us, et bibendum nibh elit in nisi. Vestibulum nec" +
                        " placerat lectus. Nulla pretium purus id risus por" +
                        "ttitor faucibus. Maecenas pretium erat lorem, eu tin" +
                        "cidunt massa molestie et. Phasellus sed consequat nun" +
                        "c. Pellentesque accumsan luctus elit et tempus. adi" +
                        "piscing elit. Proin nunc sapien, tincidunt vitae quam" +
                        " quis, rutrum tristique risus. Quisque non dignissim " +
                        "leo. Nam fringilla elit eu vulputate mattis. Nullam " +
                        "viverra tincidunt dui, ultrices rutrum turpis malesuad" +
                        "a at. Aliquam et pellentesque dolor. Phasellus iaculi" +
                        "s quam est, vel ultrices nulla tincidunt ac. In at sa" +
                        "pien feugiat neque dapibus feugiat et ac urna. Fusce " +
                        "eget elit nunc. Cras eu tortor quis enim viverra efficitur.",
                color = Colors.white,
                style = body().copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                style = h3(),
                color = Colors.white,
                text = stringResource(Res.string.terms_of_service_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing el" +
                        "it. Vestibulum sit amet nisi eget felis ornare egest" +
                        "as non porta ante. Aenean vitae aliquam massa. Sed al" +
                        "iquam quam id justo semper consectetur. Vestibulum por" +
                        "ta ipsum id odio auctor suscipit. Vestibulum ante ipsum " +
                        "primis in faucibus orci luctus et ultrices posuere cubi" +
                        "lia curae; Morbi blandit sem non lorem vehicula placerat" +
                        ". Vivamus dictum porttitor dui, in consequat purus eleifen" +
                        "d at. Donec magna massa, vehicula at odio eget, convallis r" +
                        "honcus odio. Etiam porttitor nunc ligula, non vehicula enim " +
                        "luctus a. Donec quis lorem arcu. Sed sed ultricies purus. Ma" +
                        "ecenas malesuada vel nulla sit Ut at pulvinar enim. Donec ut" +
                        " massa eget enim commodo tristique sit amet vitae turpis. Se" +
                        "d sodales pulvinar cursus. Donec posuere justo et consequat " +
                        "eleifend. Nulla nibh quam, pellentesque eu dui nec, condimen" +
                        "tum fermentum ante. Pellentesque eleifend, mi non posuere susc" +
                        "ipit, arcu mi vestibulum velit, vitae rhoncus nunc purus id qu" +
                        "am. Integer egestas blandit finibus. Proin interdum, justo in " +
                        "cursus pellentesque, massa erat suscipit mi, a vulputate metus" +
                        " libero eu elit. amet elementum. Pellentesque dictum turpis u" +
                        " urna ultrices porta. Etiam fringilla nec quam ac sodales. Pr" +
                        "oin nunc sapien, tincidunt vitae quam quis, rutrum tristique " +
                        "risus. Quisque non dignissim leo. Nam fringilla elit eu vulpu" +
                        "tate mattis. Nullam viverra tincidunt dui, ultrices rutrum tu" +
                        "rpis malesuada at. Aliquam et pellentesque dolor. Phasellus i" +
                        "aculis quam est, vel ultrices nulla tincidunt ac. In at sapie" +
                        "n feugiat neque dapibus feugiat et ac urna. Fusce eget elit n" +
                        "unc. Cras eu tortor quis enim viverra efficitur.",
                color = Colors.white,
                style = body().copy(textAlign = TextAlign.Center)
            )
        }
    }

}