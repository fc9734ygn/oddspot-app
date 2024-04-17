package domain.use_case.wishlist

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.WishlistRepository
import domain.util.Resource
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class AddToWishlistUseCase(
    private val wishlistRepository: WishlistRepository
) {
    operator fun invoke(spotId: Int) = flow {
        emit(Resource.Loading())
        wishlistRepository.addSpotToWishlist(spotId).mapError {
            Logger.e("AddToWishlistUseCase", it.throwable)
            emit(Resource.Error(it))
            return@flow
        }
        emit(Resource.Success(Unit))
    }
}