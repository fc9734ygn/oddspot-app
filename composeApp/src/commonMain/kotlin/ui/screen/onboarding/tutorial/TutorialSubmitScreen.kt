package ui.screen.onboarding.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.img_tutorial_submit_one
import oddspot_app.composeapp.generated.resources.img_tutorial_submit_two
import oddspot_app.composeapp.generated.resources.tutorial_button
import oddspot_app.composeapp.generated.resources.tutorial_submit_description
import oddspot_app.composeapp.generated.resources.tutorial_submit_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.util.Colors
import ui.util.body
import ui.util.h1

class TutorialSubmitScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow

        Column (
            modifier = Modifier
                .background(color = Colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 48.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(Res.drawable.img_tutorial_submit_one),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 64.dp),
                text = stringResource(Res.string.tutorial_submit_title),
                color = Colors.white,
                style = h1()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                text = stringResource(Res.string.tutorial_submit_description),
                color = Colors.white,
                style = body()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painterResource(Res.drawable.img_tutorial_submit_two),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth(),
                text = stringResource(Res.string.tutorial_button),
                onClick = { navigator.push(TutorialSafetyScreen()) }
            )
        }
    }
}