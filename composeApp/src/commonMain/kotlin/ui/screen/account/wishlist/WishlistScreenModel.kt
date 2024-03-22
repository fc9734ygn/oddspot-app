package ui.screen.account.wishlist

import domain.use_case.wishlist.GetWishlistUseCase
import domain.use_case.wishlist.RemoveFromWishlistUseCase
import org.koin.core.annotation.Factory
import ui.base.BaseScreenModel
import util.Event

@Factory
class WishlistScreenModel(
    private val getWishlistUseCase: GetWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase
) : BaseScreenModel<WishlistScreenState>(WishlistScreenState.Initial) {

    init {
        getData()
    }

    private fun getData() {
        getWishlistUseCase().collectResource(
            onSuccess = { items ->
                updateState {
                    copy(
                        items = items,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState {
                    copy(
                        event = Event(WishlistScreenEvent.Error),
                        isLoading = false
                    )
                }
            },
            onLoading = {
                updateState {
                    copy(
                        isLoading = true
                    )
                }
            }
        )
    }

    fun removeWishlistItem(spotId: Int){
        removeFromWishlistUseCase(spotId).collectResource(
            onError = {
                updateState {
                    copy(
                        event = Event(WishlistScreenEvent.Error),
                    )
                }
            }
        )
    }
}