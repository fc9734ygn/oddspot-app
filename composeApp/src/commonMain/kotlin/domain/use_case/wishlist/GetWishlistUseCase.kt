package domain.use_case.wishlist

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import data.repository.WishlistRepository
import domain.use_case.spot.model.WishlistItemModel
import domain.use_case.spot.model.toAccessibility
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory


@Factory
class GetWishlistUseCase(
    private val wishlistRepository: WishlistRepository,
    private val spotRepository: SpotRepository
) {
    operator fun invoke(): Flow<Resource<List<WishlistItemModel>>> = flow {
        emit(Resource.Loading())

        wishlistRepository.updateWishlist().mapError {
            // We do not want to expose this error and use cached data
        }

        wishlistRepository.getWishlistFlow().collect { wishlistIds ->
            val wishlistItems = wishlistIds.mapNotNull { id ->
                spotRepository.getSpotById(id).getOrElse {
                    // Skip this item if it fails to fetch
                    null
                }?.let { spot ->
                        WishlistItemModel(
                            spotId = spot.id.toInt(),
                            title = spot.title,
                            mainImage = spot.image_url,
                            accessibility = spot.accessibility.toAccessibility()
                        )
                    }
            }

            emit(Resource.Success(wishlistItems))
        }
    }
}