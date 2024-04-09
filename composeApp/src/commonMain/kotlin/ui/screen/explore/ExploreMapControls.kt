package ui.screen.explore

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_map_type
import oddspot_app.composeapp.generated.resources.ic_my_location
import oddspot_app.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.Colors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BoxScope.ExploreMapControls(
    onSubmitSpotClick: () -> Unit,
    onMapTypeClick: () -> Unit,
    onMyLocationClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.BottomEnd),
    ) {
        FloatingActionButton(
            onClick = onSubmitSpotClick,
            backgroundColor = Colors.red,
            contentColor = Colors.black,
        ) {
            Icon(painterResource(Res.drawable.ic_plus), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(8.dp))
        FloatingActionButton(
            onClick = onMapTypeClick,
            backgroundColor = Colors.lightGrey,
            contentColor = Colors.black,
        ) {
            Icon(painterResource(Res.drawable.ic_map_type), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(8.dp))
        FloatingActionButton(
            onClick = onMyLocationClick,
            backgroundColor = Colors.lightGrey,
            contentColor = Colors.black,
        ) {
            Icon(painterResource(Res.drawable.ic_my_location), contentDescription = null)
        }
    }
}