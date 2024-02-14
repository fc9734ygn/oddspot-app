package ui.screen.onboarding.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.util.body
import ui.util.h1

class TutorialSubmitScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .background(color = colorResource(MR.colors.background))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier.height(300.dp).width(200.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 64.dp)
                    .background(color = colorResource(MR.colors.red))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp),
                    text = stringResource(MR.strings.tutorial_submit_title),
                    color = colorResource(MR.colors.white),
                    style = h1()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 48.dp),
                    text = stringResource(MR.strings.tutorial_submit_description),
                    color = colorResource(MR.colors.white),
                    style = body()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .width(200.dp)
                        .background(color = colorResource(MR.colors.red))
                )
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    modifier = Modifier
                        .padding(horizontal = 48.dp)
                        .padding(bottom = 48.dp)
                        .fillMaxWidth(),
                    text = stringResource(MR.strings.tutorial_button),
                    onClick = { navigator.push(TutorialSafetyScreen()) }
                )
            }
        }
    }
}