package ui.screen.account.visited

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import oddspot_app.composeapp.generated.resources.ic_arrow_back
import oddspot_app.composeapp.generated.resources.visited_spots_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.snackbar.GenericErrorSnackbar
import ui.util.Colors
import ui.util.Consume
import ui.util.h1

class VisitedSpotsScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<VisitedSpotsScreenModel>()
        val state by screenModel.state.collectAsState()

        state.event?.Consume {
            when (it) {
                VisitedSpotsScreenEvent.Error -> GenericErrorSnackbar(snackbarHostState)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = Colors.darkGrey,
                        modifier = Modifier.clickable { navigator.pop() }
                    )
                }
            }
            item {
                Text(
                    text = stringResource(Res.string.visited_spots_title),
                    style = h1()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(state.items) {
                VisitedSpotItem(state = it) {
//                        navigator.push(VisitedSpotDetailScreen(it.id)) // TODO implement
                }
            }
        }
    }
}