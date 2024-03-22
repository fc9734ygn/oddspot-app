package domain.use_case.wishlist

import com.github.michaelbull.result.mapError
import data.repository.WishlistRepository
import domain.util.Resource
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class RemoveFromWishlistUseCase(
    private val wishlistRepository: WishlistRepository
) {
    operator fun invoke(spotId: Int) = flow {
        emit(Resource.Loading())
        wishlistRepository.removeSpotFromWishlist(spotId).mapError {
            emit(Resource.Error(it))
            return@flow
        }
        emit(Resource.Success(Unit))
    }
}