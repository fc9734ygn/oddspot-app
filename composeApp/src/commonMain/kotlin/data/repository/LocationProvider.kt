package data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.annotation.Singleton

@Singleton
class LocationProvider(
//    val context: Context,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getCurrentLocation(): Pair<Double,Double>? {
       TODO()
//        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
//        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val locationManager = context.getSystemService(
//            Context.LOCATION_SERVICE
//        ) as LocationManager
//
//        val isGpsEnabled = locationManager
//            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//
//        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
//            return null
//        }
//        return suspendCancellableCoroutine { cont ->
//            fusedLocationProviderClient.lastLocation.apply {
//                if (isComplete) {
//                    if (isSuccessful) {
//                        cont.resume(Pair(result.latitude, result.longitude)) {} // Resume coroutine with location result
//                    } else {
//                        cont.resume(null) {} // Resume coroutine with null location result
//                    }
//                    return@suspendCancellableCoroutine
//                }
//                addOnSuccessListener {
//                    if(it == null){
//                        cont.resume(null) {} // Resume coroutine with null location result
//                        return@addOnSuccessListener
//                    }
//                    cont.resume(Pair(result.latitude, result.longitude)) {}  // Resume coroutine with location result
//                }
//                addOnFailureListener {
//                    cont.resume(null) {} // Resume coroutine with null location result
//                }
//                addOnCanceledListener {
//                    cont.cancel() // Cancel the coroutine
//                }
//            }
//        }
    }
}