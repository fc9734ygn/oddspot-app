
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import ui.util.Location

actual class LocationProvider() {
    companion object{
        const val LOCATION_REQUEST_INTERVAL = 10000L
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    actual suspend fun getUserLocation(): Result<Location, UserLocationError> {

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(appContext)
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            appContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = appContext.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission) {
            return Err(UserLocationError.LocationPermissionDenied)
        }
        if (!isGpsEnabled) {
            return Err(UserLocationError.GpsProviderDisabled)
        }
        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(Ok(Location(result.latitude, result.longitude))) {}
                    } else {
                        cont.resume(Err(UserLocationError.GeneralFailure)) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    if (it == null) {
                        cont.resume(Err(UserLocationError.NoLocationAvailable)) {}
                        return@addOnSuccessListener
                    }
                    cont.resume(Ok(Location(result.latitude, result.longitude))) {}
                }
                addOnFailureListener {
                    cont.resume(Err(UserLocationError.LocationFetchFailure)) {}
                }
                addOnCanceledListener {
                    cont.cancel() // Cancel the coroutine
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    actual fun getUserLocationFlow(): Flow<Location> =
        callbackFlow {

            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(appContext)

            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.forEach { location ->
                        trySend(Location(location.latitude, location.longitude)).isSuccess
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                close(e) // Close the flow with an error if location updates cannot be started
            }

            awaitClose {
                // Clean up when the flow collector is done
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
}