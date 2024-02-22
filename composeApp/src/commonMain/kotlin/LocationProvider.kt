import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import ui.util.Location

expect class LocationProvider {
    suspend fun getUserLocation(): Result<Location, UserLocationError>
    fun getUserLocationFlow(): Flow<Location>
}