package ui.screen.onboarding.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.component.checkbox.PrimaryCheckbox
import ui.component.snackbar.GenericErrorSnackbar
import ui.screen.onboarding.get_started.GetStartedScreen
import ui.screen.policy.PolicyScreen
import ui.util.Consume
import ui.util.body
import ui.util.footnote
import ui.util.h1

class TutorialPrivacyScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<TutorialScreenModel>()
        val state by screenModel.state.collectAsState()

        state.event?.Consume {
            when (it) {
                TutorialEvent.Success -> navigator.push(GetStartedScreen())
                TutorialEvent.Error -> GenericErrorSnackbar(snackbarHostState)
            }
        }

        Box(
            modifier = Modifier
                .background(color = colorResource(MR.colors.background))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp).padding(top = 48.dp),
                    text = stringResource(MR.strings.tutorial_privacy_title),
                    color = colorResource(MR.colors.white),
                    style = h1()
                )
                Box(
                    modifier = Modifier.height(300.dp).width(200.dp)
                        .background(color = colorResource(MR.colors.red))
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 48.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(MR.strings.tutorial_privacy_description),
                    color = colorResource(MR.colors.white),
                    style = body()
                )
                Spacer(modifier = Modifier.height(16.dp))
                val privacyAndTermsReference = buildAnnotatedString {
                    append(stringResource(MR.strings.tutorial_privacy_terms_description_prefix))
                    append(" ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(MR.strings.tutorial_privacy_policy))
                    }
                    append(" ")
                    append(stringResource(MR.strings.tutorial_privacy_and))
                    append(" ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(MR.strings.tutorial_privacy_terms))
                    }
                    append(stringResource(MR.strings.tutorial_privacy_suffix))
                }
                Text(
                    modifier = Modifier.clickable { navigator.push(PolicyScreen()) },
                    text = privacyAndTermsReference,
                    color = colorResource(MR.colors.dark_grey),
                    style = footnote()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PrimaryCheckbox(
                        checked = state.isCheckboxChecked,
                        onCheckedChange = screenModel::onCheckboxCheckedChange
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    val privacyAndTermsAgreementText = buildAnnotatedString {
                        append(stringResource(MR.strings.tutorial_privacy_checkbox_prefix))
                        append(" ")
                        append(stringResource(MR.strings.tutorial_privacy_policy))
                        append(" ")
                        append(stringResource(MR.strings.tutorial_privacy_and))
                        append(" ")
                        append(stringResource(MR.strings.tutorial_privacy_terms))
                        append(stringResource(MR.strings.tutorial_privacy_suffix))
                    }
                    Text(
                        modifier = Modifier.clickable { screenModel.onCheckboxCheckedChange(!state.isCheckboxChecked) },
                        text = privacyAndTermsAgreementText,
                        color = colorResource(MR.colors.dark_grey),
                        style = footnote().copy(textAlign = TextAlign.Start)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .fillMaxWidth(),
                    text = stringResource(MR.strings.tutorial_button),
                    onClick = screenModel::onNextClick,
                    enabled = state.isCheckboxChecked
                )
            }
        }
    }

}