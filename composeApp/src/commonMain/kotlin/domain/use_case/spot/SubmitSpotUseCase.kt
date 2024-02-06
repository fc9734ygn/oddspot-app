package domain.use_case.spot

import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class SubmitSpotUseCase(
    private val spotRepository: SpotRepository,
) {
    operator fun invoke(
        title: String,
        description: String,
        location: Pair<Double, Double>,
        difficulty: Int,
        imageUri: String
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        spotRepository.submitSpot(
            title,
            description,
            location,
            difficulty,
            imageUri
        ).mapError {
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(Unit))
    }
}