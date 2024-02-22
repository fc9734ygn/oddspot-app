import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import ui.util.Location

actual class LocationProvider {
    actual suspend fun getUserLocation(): Result<Location, UserLocationError> {
        TODO("Not yet implemented")
    }

    actual fun getUserLocationFlow(): Flow<Location> {
        TODO("Not yet implemented")
    }
}