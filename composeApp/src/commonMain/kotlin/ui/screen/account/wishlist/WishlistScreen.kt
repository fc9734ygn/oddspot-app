package ui.screen.account.wishlist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.wishlist_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.snackbar.GenericErrorSnackbar
import ui.util.Consume
import ui.util.h1

class WishlistScreen : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<WishlistScreenModel>()
        val state by screenModel.state.collectAsState()

        state.event?.Consume {
            when (it) {
                WishlistScreenEvent.Error -> GenericErrorSnackbar(snackbarHostState)
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
        ) {
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(Res.string.wishlist_title),
                    style = h1()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(state.items) {
                WishlistItem(state = it, onClick = {}, onRemoveClick = {})
            }
        }
    }
}