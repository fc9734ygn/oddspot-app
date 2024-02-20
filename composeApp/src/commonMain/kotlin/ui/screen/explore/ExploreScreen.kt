package ui.screen.explore

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import ui.base.BaseScreen

class ExploreScreen : BaseScreen() {

    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
//        snackbarHostStateval navigator = LocalNavigator.currentOrThrow
//        val screenModel = getScreenModel<ExploreScreenModel>()
//        val state by screenModel.state.collectAsState()
//
//
//        val currentUserLocation = state.userCurrentLocation ?: return
//        val currentLatLng = LatLng(currentUserLocation.first, currentUserLocation.second)
//
//        Column(
//            Modifier
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            val properties = remember {
//                mutableStateOf(MapProperties(isMyLocationEnabled = true, mapType = MapType.SATELLITE))
//            }
//
//            val cameraPositionState = rememberCameraPositionState {
//                position = CameraPosition.fromLatLngZoom(currentLatLng, DEFAULT_ZOOM)
//            }
//            val uiSettings = remember {
//                mutableStateOf(
//                    MapUiSettings(
//                        myLocationButtonEnabled = true,
//                        zoomControlsEnabled = false
//                    )
//                )
//            }
//
//            GoogleMap(
//                cameraPositionState = cameraPositionState,
//                properties = properties.value,
//                uiSettings = uiSettings.value,
//            ) {
//                state.unknownSpots.forEach { spot ->
//                    val icon = context.bitmapDescriptorFromVector(R.drawable.ic_unknown_spot_marker)
//
//                    Marker(
//                        state = MarkerState(
//                            position = LatLng(
//                                spot.coordinates.first,
//                                spot.coordinates.second
//                            )
//                        ),
//                        icon = icon,
//                    )
//                }
//            }
//        }
    }
}