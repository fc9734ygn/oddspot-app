package ui.screen.onboarding.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.img_tutorial_privacy
import oddspot_app.composeapp.generated.resources.tutorial_button
import oddspot_app.composeapp.generated.resources.tutorial_privacy_and
import oddspot_app.composeapp.generated.resources.tutorial_privacy_checkbox_prefix
import oddspot_app.composeapp.generated.resources.tutorial_privacy_description
import oddspot_app.composeapp.generated.resources.tutorial_privacy_policy
import oddspot_app.composeapp.generated.resources.tutorial_privacy_suffix
import oddspot_app.composeapp.generated.resources.tutorial_privacy_terms
import oddspot_app.composeapp.generated.resources.tutorial_privacy_terms_description_prefix
import oddspot_app.composeapp.generated.resources.tutorial_privacy_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.component.checkbox.PrimaryCheckbox
import ui.component.snackbar.GenericErrorSnackbar
import ui.screen.onboarding.get_started.GetStartedScreen
import ui.screen.policy.PolicyScreen
import ui.util.Colors
import ui.util.Consume
import ui.util.body
import ui.util.footnote
import ui.util.h1

class TutorialPrivacyScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
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

        Column(
            modifier = Modifier
                .background(color = Colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(48.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.tutorial_privacy_title),
                color = Colors.white,
                style = h1()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painterResource(Res.drawable.img_tutorial_privacy),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(Res.string.tutorial_privacy_description),
                    color = Colors.white,
                    style = body()
                )
                Spacer(modifier = Modifier.height(8.dp))
                val privacyAndTermsReference = buildAnnotatedString {
                    append(stringResource(Res.string.tutorial_privacy_terms_description_prefix))
                    append(" ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(Res.string.tutorial_privacy_policy))
                    }
                    append(" ")
                    append(stringResource(Res.string.tutorial_privacy_and))
                    append(" ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(Res.string.tutorial_privacy_terms))
                    }
                    append(stringResource(Res.string.tutorial_privacy_suffix))
                }
                Text(
                    modifier = Modifier.clickable { navigator.push(PolicyScreen()) },
                    text = privacyAndTermsReference,
                    color = Colors.darkGrey,
                    style = footnote()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                PrimaryCheckbox(
                    checked = state.isCheckboxChecked,
                    onCheckedChange = screenModel::onCheckboxCheckedChange
                )
                Spacer(modifier = Modifier.width(12.dp))
                val privacyAndTermsAgreementText = buildAnnotatedString {
                    append(stringResource(Res.string.tutorial_privacy_checkbox_prefix))
                    append(" ")
                    append(stringResource(Res.string.tutorial_privacy_policy))
                    append(" ")
                    append(stringResource(Res.string.tutorial_privacy_and))
                    append(" ")
                    append(stringResource(Res.string.tutorial_privacy_terms))
                    append(stringResource(Res.string.tutorial_privacy_suffix))
                }
                Text(
                    modifier = Modifier.clickable { screenModel.onCheckboxCheckedChange(!state.isCheckboxChecked) },
                    text = privacyAndTermsAgreementText,
                    color = Colors.darkGrey,
                    style = footnote().copy(textAlign = TextAlign.Start)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.tutorial_button),
                onClick = screenModel::onNextClick,
                isEnabled = state.isCheckboxChecked
            )
        }
    }

}