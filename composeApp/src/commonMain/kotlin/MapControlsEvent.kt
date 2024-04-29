import domain.util.Location

sealed class MapControlsEvent {
    data class AnimateToLocation(val location: Location) : MapControlsEvent()
    data class MapTypeChange(val mapType: Int) :
        MapControlsEvent() // From com.google.android.gms.maps.GoogleMaps NONE = 0, NORMAL = 1, SATELLITE = 2, TERRAIN = 3, HYBRID = 4
}