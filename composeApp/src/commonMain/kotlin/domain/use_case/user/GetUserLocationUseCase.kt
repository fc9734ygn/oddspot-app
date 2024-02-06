package domain.use_case.user

import data.repository.LocationProvider
import domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetUserLocationUseCase(
    private val locationProvider: LocationProvider
){
    operator fun invoke() : Flow<Resource<Pair<Double, Double>>> = flow{
        emit(Resource.Loading())
        val coordinates = locationProvider.getCurrentLocation()
        if (coordinates == null){
            emit(Resource.Error())
            return@flow
        }
        emit(Resource.Success(coordinates))
    }
}