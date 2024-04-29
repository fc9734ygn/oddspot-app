import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import domain.util.Location

expect class LocationProvider {
    suspend fun getUserLocation(): Result<Location, UserLocationError>
    fun getUserLocationFlow(): Flow<Location>
}