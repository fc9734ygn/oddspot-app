package ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.account_button_logout
import oddspot_app.composeapp.generated.resources.account_change_username_dialog_description
import oddspot_app.composeapp.generated.resources.account_change_username_dialog_title
import oddspot_app.composeapp.generated.resources.account_item_about
import oddspot_app.composeapp.generated.resources.account_item_feedback
import oddspot_app.composeapp.generated.resources.account_item_privacy
import oddspot_app.composeapp.generated.resources.account_item_settings
import oddspot_app.composeapp.generated.resources.account_item_submitted_spots
import oddspot_app.composeapp.generated.resources.account_item_visited_spots
import oddspot_app.composeapp.generated.resources.account_item_wishlist
import oddspot_app.composeapp.generated.resources.ic_account_my_settings
import oddspot_app.composeapp.generated.resources.ic_account_submitted_spots
import oddspot_app.composeapp.generated.resources.ic_account_visited_spots
import oddspot_app.composeapp.generated.resources.ic_account_wishlist
import oddspot_app.composeapp.generated.resources.ic_bottom_nav_account
import oddspot_app.composeapp.generated.resources.ic_edit
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sendEmailAction
import ui.base.BaseTabScreen
import ui.base.NavigatorModel
import ui.component.button.PrimaryButton
import ui.component.dialog.InputDialog
import ui.component.snackbar.GenericErrorSnackbar
import ui.screen.onboarding.welcome.WelcomeScreen
import ui.screen.policy.PolicyScreen
import ui.util.Colors
import ui.util.Consume
import ui.util.button1
import ui.util.h3
import ui.util.input

private const val MAX_USERNAME_LENGTH = 20
private const val MIN_USERNAME_LENGTH = 4

class AccountScreen : BaseTabScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<AccountScreenModel>()
        val navigatorModel = navigator.rememberNavigatorScreenModel { NavigatorModel() }
        val state by screenModel.state.collectAsState()

        state.event?.Consume {
            when (it) {
                AccountScreenEvent.GoToWelcomeScreen -> navigator.replaceAll(WelcomeScreen())
                AccountScreenEvent.Error -> GenericErrorSnackbar(snackbarHostState)
            }
        }

        if (state.changeUsernameDialogState.show) {
            InputDialog(
                onDismiss = { screenModel.onDismissUsernameChangeDialog() },
                title = stringResource(Res.string.account_change_username_dialog_title),
                description = stringResource(
                    Res.string.account_change_username_dialog_description,
                    MIN_USERNAME_LENGTH,
                    MAX_USERNAME_LENGTH
                ),
                inputValue = state.changeUsernameDialogState.input,
                onInputValueChange = { screenModel.onUsernameInputChange(it) },
                onConfirmClick = { screenModel.onConfirmUsernameChangeClick() },
                isLoading = state.changeUsernameDialogState.isLoading,
                inputPlaceholder = state.username,
                maxInputLength = MAX_USERNAME_LENGTH
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Colors.background)
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(124.dp)
                        .background(Colors.red, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_bottom_nav_account),
                        contentDescription = null,
                        tint = Colors.black,
                        modifier = Modifier.size(64.dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = state.username, style = h3())
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .padding(bottom = 16.dp)
                            .size(24.dp)
                            .clickable { screenModel.onChangeUsernameClick() },
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                AccountButtonItem(
                    text = Res.string.account_item_submitted_spots,
                    onClick = { },
                    iconRes = Res.drawable.ic_account_submitted_spots
                )
                Spacer(modifier = Modifier.height(12.dp))
                AccountButtonItem(
                    text = Res.string.account_item_visited_spots,
                    onClick = { },
                    iconRes = Res.drawable.ic_account_visited_spots
                )
                Spacer(modifier = Modifier.height(12.dp))
                AccountButtonItem(
                    text = Res.string.account_item_wishlist,
                    onClick = { },
                    iconRes = Res.drawable.ic_account_wishlist
                )
                Spacer(modifier = Modifier.height(12.dp))
                AccountButtonItem(
                    text = Res.string.account_item_settings,
                    onClick = { },
                    iconRes = Res.drawable.ic_account_my_settings
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(Res.string.account_item_about),
                        style = input().copy(textAlign = TextAlign.Start, color = Colors.darkGrey),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.clickable {
                            sendEmailAction(body = screenModel.getFeedbackEmailBody())
                        },
                        text = stringResource(Res.string.account_item_feedback),
                        style = input().copy(textAlign = TextAlign.Start, color = Colors.darkGrey),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.clickable {
                            navigator.push(PolicyScreen())
                        },
                        text = stringResource(Res.string.account_item_privacy),
                        style = input().copy(textAlign = TextAlign.Start, color = Colors.darkGrey),
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    text = stringResource(Res.string.account_button_logout),
                    onClick = {
                        navigatorModel.resetCurrentBottomNavTab()
                        screenModel.onLogoutClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AccountButtonItem(
    text: StringResource,
    onClick: () -> Unit,
    iconRes: DrawableResource
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(54.dp)
            .background(
                color = Colors.background,
                shape = RoundedCornerShape(32.dp)
            )
            .border(
                width = 2.dp,
                color = Colors.red,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painterResource(iconRes),
            contentDescription = null,
            tint = Colors.red,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = stringResource(text), style = button1())
    }
}