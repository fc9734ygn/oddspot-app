import domain.util.Location

sealed class MapControlsEvent {
    data class AnimateToLocation(val location: Location) : MapControlsEvent()
    data class MapTypeChange(val mapType: Int) : MapControlsEvent()
}