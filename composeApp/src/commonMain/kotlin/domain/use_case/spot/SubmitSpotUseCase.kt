package domain.use_case.spot

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.mapError
import data.repository.SpotRepository
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import ui.util.Location

@Factory
class SubmitSpotUseCase(
    private val spotRepository: SpotRepository,
) {
    operator fun invoke(
        title: String,
        description: String,
        location: Location,
        difficulty: Int,
        image: ByteArray,
        isArea: Boolean
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        spotRepository.submitSpot(
            title,
            description,
            location,
            difficulty,
            image,
            isArea
        ).mapError {
            Logger.e("SubmitSpotUseCase", it)
            emit(Resource.Error())
            return@flow
        }

        emit(Resource.Success(Unit))
    }
}