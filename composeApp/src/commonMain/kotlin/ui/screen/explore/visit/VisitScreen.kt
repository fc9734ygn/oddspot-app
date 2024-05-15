package ui.screen.explore.visit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_dislike
import oddspot_app.composeapp.generated.resources.ic_like
import oddspot_app.composeapp.generated.resources.ic_visit
import oddspot_app.composeapp.generated.resources.visit_screen_button
import oddspot_app.composeapp.generated.resources.visit_screen_no_rating_error
import oddspot_app.composeapp.generated.resources.visit_screen_rate_dislike
import oddspot_app.composeapp.generated.resources.visit_screen_rate_like
import oddspot_app.composeapp.generated.resources.visit_screen_rate_visit_title
import oddspot_app.composeapp.generated.resources.visit_screen_spot_visited_snackbar
import oddspot_app.composeapp.generated.resources.visit_screen_subtitle
import oddspot_app.composeapp.generated.resources.visit_screen_title
import oddspot_app.composeapp.generated.resources.visit_screen_total_visits_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.BackButton
import ui.component.button.PrimaryButton
import ui.component.snackbar.GenericErrorSnackbar
import ui.component.snackbar.ShowSnackBar
import ui.screen.submit.ImageSelector
import ui.util.Colors
import ui.util.Consume
import ui.util.body
import ui.util.h1
import ui.util.noRippleClickable

class VisitScreen(
    private val spotId: Int
) : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {

        val screenModel = getScreenModel<VisitScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        state.event?.Consume {
            when (it) {
                VisitScreenEvent.GenericError -> {
                    GenericErrorSnackbar(snackbarHostState)
                }
                VisitScreenEvent.NoRatingError -> {
                    ShowSnackBar(
                        snackbarHostState = snackbarHostState,
                        message = stringResource(Res.string.visit_screen_no_rating_error)
                    )
                }
                VisitScreenEvent.VisitSuccess -> {
                    ShowSnackBar(
                        snackbarHostState = snackbarHostState,
                        message = stringResource(Res.string.visit_screen_spot_visited_snackbar)
                    ){
                        navigator.pop()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Colors.background)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BackButton { navigator.pop() }
                Text(
                    style = h1(),
                    color = Colors.white,
                    text = stringResource(Res.string.visit_screen_title)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    style = body(),
                    color = Colors.white,
                    text = stringResource(Res.string.visit_screen_subtitle)
                )
                Spacer(modifier = Modifier.height(24.dp))
                ImageSelector(
                    modifier = Modifier.fillMaxWidth(),
                    state.image?.data,
                    screenModel::onImageSelected
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    style = h1(),
                    color = Colors.white,
                    text = stringResource(Res.string.visit_screen_rate_visit_title)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    Column(
                        modifier = Modifier.noRippleClickable { screenModel.onLikeClick() },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_like),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = if (state.visitRating == VisitRating.Like) Colors.red else Colors.white
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            style = body(),
                            color = if (state.visitRating == VisitRating.Like) Colors.red else Colors.white,
                            text = stringResource(Res.string.visit_screen_rate_like),
                        )
                    }
                    Spacer(modifier = Modifier.width(48.dp))
                    Column(
                        modifier = Modifier.noRippleClickable { screenModel.onDislikeClick() },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_dislike),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = if (state.visitRating == VisitRating.Dislike) Colors.red else Colors.white,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            style = body(),
                            color = if (state.visitRating == VisitRating.Dislike) Colors.red else Colors.white,
                            text = stringResource(Res.string.visit_screen_rate_dislike)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    style = h1(),
                    color = Colors.white,
                    text = stringResource(Res.string.visit_screen_total_visits_title)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(Res.drawable.ic_visit),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Colors.white
                    )
                    Text(
                        style = h1(),
                        color = Colors.white,
                        text = state.userTotalVisits?.toString() ?: ""
                    )
                }
            }
            Column {
                PrimaryButton(
                    text = stringResource(Res.string.visit_screen_button),
                    onClick = { screenModel.onMarkAsVisitedClick(spotId) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 48.dp),
                    isLoading = state.isLoading
                )
            }
        }
    }
}