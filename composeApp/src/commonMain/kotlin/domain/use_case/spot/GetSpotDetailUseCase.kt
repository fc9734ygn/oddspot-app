package domain.use_case.spot

import com.github.michaelbull.result.getOrElse
import data.repository.SpotRepository
import domain.use_case.spot.model.Accessibility
import domain.use_case.spot.model.SpotDetail
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetSpotDetailUseCase(
    private val spotRepository: SpotRepository,
) {

    operator fun invoke(id: String): Flow<Resource<SpotDetail>> = flow {
        emit(Resource.Loading())

        val spot = spotRepository.getSpotById(id).getOrElse {
            emit(Resource.Error())
            return@flow
        }

        // TODO: Implement
        val spotDetail = SpotDetail(
            id = spot.id,
            title = spot.title,
            description = spot.description,
            imageUrl = spot.imageUrl,
            inWishlist = spot.inWishlist,
            amountOfVisits = 1,
            accessibility = Accessibility.Easy,
            visitImages = listOf("https://media.istockphoto.com/id/1361394182/photo/funny-british-shorthair-cat-portrait-looking-shocked-or-surprised.jpg?b=1&s=612x612&w=0&k=20&c=-niqIUX8Kfiyn50xgUzxxUYX6H2q9BlGc3PX5PVM-iA=",
                "https://static.scientificamerican.com/sciam/cache/file/2AE14CDD-1265-470C-9B15F49024186C10_source.jpg?w=1200",
                "https://cdn.mos.cms.futurecdn.net/yzV5i2F35i9RozwSeFLPJV-1200-80.jpg",
                "https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_square.jpg",
                "https://cdn.britannica.com/25/172925-050-DC7E2298/black-cat-back.jpg"),
            isLocked = false,
        )

        emit(Resource.Success(spotDetail))
    }
}