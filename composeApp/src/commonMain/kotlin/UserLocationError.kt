sealed class UserLocationError{
    data object LocationPermissionDenied: UserLocationError()
    data object GpsProviderDisabled: UserLocationError()
    data object LocationFetchFailure : UserLocationError()
    data object NoLocationAvailable : UserLocationError()
    data object GeneralFailure : UserLocationError()
}