package ui.screen.account.wishlist

import androidx.compose.runtime.Immutable
import domain.use_case.spot.model.WishlistItemModel
import util.Event

@Immutable
data class WishlistScreenState(
    val isLoading: Boolean,
    val items: List<WishlistItemModel>,
    val event: Event<WishlistScreenEvent>?
) {
    companion object {
        val Initial = WishlistScreenState(
            isLoading = false,
            items = emptyList(),
            event = null
        )
    }
}

sealed class WishlistScreenEvent {
    data object Error : WishlistScreenEvent()
}
