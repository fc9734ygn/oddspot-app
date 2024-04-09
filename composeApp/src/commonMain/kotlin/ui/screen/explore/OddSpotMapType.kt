package ui.screen.explore

// Corresponds to // From com.google.android.gms.maps.GoogleMaps
// NONE = 0, NORMAL = 1, SATELLITE = 2, TERRAIN = 3, HYBRID = 4
enum class OddSpotMapType(val value: Int) {
    NORMAL(1), SATELLITE(2), HYBRID(4)
}