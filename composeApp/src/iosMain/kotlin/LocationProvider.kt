
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import domain.util.Location
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
actual class LocationProvider {

    private val locationManager = CLLocationManager()
    private val locationDelegate = LocationDelegate()

    init {
        locationManager.delegate = locationDelegate
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()
    }

    actual suspend fun getUserLocation(): Result<Location, UserLocationError> = suspendCoroutine { continuation ->
        var hasResumed = false // Track whether the continuation has already been resumed

        locationManager.startUpdatingLocation()

        locationDelegate.onLocationUpdate = { location ->
            locationManager.stopUpdatingLocation()

            if (!hasResumed) {
                hasResumed = true // Mark that we're resuming the coroutine to prevent future resumption attempts
                location?.let {
                    continuation.resume(Ok(it))
                } ?: continuation.resume(Err(UserLocationError.NoLocationAvailable))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual fun getUserLocationFlow(): Flow<Location> = callbackFlow {
        locationDelegate.onLocationUpdate = { location ->
            location?.let { trySend(it).isSuccess }
        }

        locationManager.startUpdatingLocation()

        awaitClose {
            locationManager.stopUpdatingLocation()
        }
    }.flowOn(Dispatchers.Main)

    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {

        var onLocationUpdate: ((Location?) -> Unit)? = null

        @OptIn(ExperimentalForeignApi::class)
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            didUpdateLocations.firstOrNull()?.let {
                val clLocation = it as CLLocation
                clLocation.coordinate.useContents {
                    val location = Location(latitude, longitude)
                    onLocationUpdate?.invoke(location)
                }
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
        }
    }
}
