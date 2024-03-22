package domain.use_case.spot

import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import data.repository.WishlistRepository
import domain.use_case.spot.model.SpotDetail
import domain.use_case.spot.model.toAccessibility
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetSpotDetailUseCase(
    private val spotRepository: SpotRepository,
    private val wishlistRepository: WishlistRepository
) {

    operator fun invoke(id: Int): Flow<Resource<SpotDetail>> = flow {
        emit(Resource.Loading())

        val spotWithVisits = spotRepository.getSpotWithVisitsBySpotId(id).getOrElse {
            emit(Resource.Error())
            return@flow
        }

        val wishlist = wishlistRepository.getWishlist().getOrElse {
            emit(Resource.Error())
            return@flow
        }

        val spotDetail = SpotDetail(
            id = spotWithVisits.spot.id.toInt(),
            title = spotWithVisits.spot.title,
            description = spotWithVisits.spot.description,
            imageUrl = spotWithVisits.spot.image_url,
            isWishlisted = wishlist.any { it == spotWithVisits.spot.id.toInt() },
            amountOfVisits = spotWithVisits.visits.size,
            accessibility = spotWithVisits.spot.accessibility.toAccessibility(),
            visitImages = spotWithVisits.visits.map { it.image_url },
            isLocked = false, // TODO: Implement
        )

        emit(Resource.Success(spotDetail))
    }
}