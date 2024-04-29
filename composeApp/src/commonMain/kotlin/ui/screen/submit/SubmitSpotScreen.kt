package ui.screen.submit

import LockedMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.use_case.spot.SPOT_RADIUS_METERS
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_arrow_back
import oddspot_app.composeapp.generated.resources.ic_info
import oddspot_app.composeapp.generated.resources.ic_marker
import oddspot_app.composeapp.generated.resources.ic_marker_button
import oddspot_app.composeapp.generated.resources.submit_spot_accessibility_title
import oddspot_app.composeapp.generated.resources.submit_spot_area_dialog_subtitle
import oddspot_app.composeapp.generated.resources.submit_spot_area_dialog_title
import oddspot_app.composeapp.generated.resources.submit_spot_area_title
import oddspot_app.composeapp.generated.resources.submit_spot_button_submit
import oddspot_app.composeapp.generated.resources.submit_spot_description
import oddspot_app.composeapp.generated.resources.submit_spot_description_error_too_long_or_too_short
import oddspot_app.composeapp.generated.resources.submit_spot_description_placeholder
import oddspot_app.composeapp.generated.resources.submit_spot_difficulties
import oddspot_app.composeapp.generated.resources.submit_spot_location_picker_topbar_title
import oddspot_app.composeapp.generated.resources.submit_spot_no_image_error
import oddspot_app.composeapp.generated.resources.submit_spot_title
import oddspot_app.composeapp.generated.resources.submit_spot_title_error_too_long_or_too_short
import oddspot_app.composeapp.generated.resources.submit_spot_title_placeholder
import oddspot_app.composeapp.generated.resources.submit_spot_toast_spot_submitted
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import ui.base.BaseScreen
import ui.component.button.PrimaryButton
import ui.component.button.SegmentedButton
import ui.component.dialog.SimpleInfoDialog
import ui.component.input.LineTextInput
import ui.component.snackbar.GenericErrorSnackbar
import ui.component.snackbar.ShowSnackBar
import ui.component.switch.SimpleSwitch
import ui.screen.submit.location_picker.LocationPickerScreen
import ui.util.CameraPosition
import ui.util.Colors
import ui.util.Consume
import domain.util.Location
import ui.util.footnote
import ui.util.h3
import domain.util.toLatLong

class SubmitSpotScreen(
    private val selectedLocation: Location? = null
) : BaseScreen() {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun ScreenContent(snackbarHostState: SnackbarHostState) {
        val screenModel = getScreenModel<SubmitSpotScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(selectedLocation) {
            selectedLocation?.let { screenModel.onLocationRefined(it) }
        }

        state.event?.Consume {
            when (it) {
                is SubmitSpotEvent.SpotSubmitted -> {
                    ShowSnackBar(
                        snackbarHostState = snackbarHostState,
                        message = stringResource(Res.string.submit_spot_toast_spot_submitted)
                    ) {
                        navigator.pop()
                    }
                }

                is SubmitSpotEvent.Error -> GenericErrorSnackbar(snackbarHostState)
                is SubmitSpotEvent.NoImage -> ShowSnackBar(
                    snackbarHostState = snackbarHostState,
                    message = stringResource(Res.string.submit_spot_no_image_error)
                )

            }
        }

        if (state.showAccessibilityInfoDialog) {
            AccessibilityInfoDialog { screenModel.onAccessibilityInfoDialogDismiss() }
        }

        if (state.showAreaInfoDialog) {
            SimpleInfoDialog(
                title = stringResource(Res.string.submit_spot_area_dialog_title),
                description = stringResource(
                    Res.string.submit_spot_area_dialog_subtitle,
                    SPOT_RADIUS_METERS
                ),
                onDismiss = { screenModel.onAreaInfoDialogDismiss() }
            )
        }

        Column(
            modifier = Modifier
                .background(Colors.background)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Colors.darkGrey,
                    modifier = Modifier.clickable { navigator.pop() }
                )
            }
            ImageSelector(
                modifier = Modifier.fillMaxWidth(),
                state.image?.data,
                screenModel::onImageSelected
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.submit_spot_title),
                style = h3(),
                textAlign = TextAlign.Start
            )
            LineTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { screenModel.onTitleChange(it) },
                placeholder = stringResource(Res.string.submit_spot_title_placeholder),
                singleLine = false
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = when (state.titleError) {
                        TitleError.TooLong, TitleError.TooShort -> {
                            stringResource(
                                Res.string.submit_spot_title_error_too_long_or_too_short,
                                MIN_TITLE_LENGTH, MAX_TITLE_LENGTH
                            )
                        }

                        null -> ""
                    },
                    style = footnote().copy(color = Colors.darkGrey, textAlign = TextAlign.Start),
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = "${state.title.length}/$MAX_TITLE_LENGTH",
                    style = footnote().copy(color = Colors.darkGrey)
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.submit_spot_description),
                style = h3(),
                textAlign = TextAlign.Start
            )
            LineTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = { screenModel.onDescriptionChange(it) },
                placeholder = stringResource(Res.string.submit_spot_description_placeholder),
                singleLine = false
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = when (state.descriptionError) {
                        DescriptionError.TooLong, DescriptionError.TooShort -> {
                            stringResource(
                                Res.string.submit_spot_description_error_too_long_or_too_short,
                                MIN_DESCRIPTION_LENGTH,
                                MAX_DESCRIPTION_LENGTH
                            )
                        }

                        null -> ""
                    },
                    style = footnote().copy(color = Colors.darkGrey, textAlign = TextAlign.Start),
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = "${state.description.length}/$MAX_DESCRIPTION_LENGTH",
                    style = footnote().copy(color = Colors.darkGrey),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.submit_spot_accessibility_title),
                    style = h3(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(16.dp)
                        .clickable { screenModel.onAccessibilityInfoClick() },
                    painter = painterResource(Res.drawable.ic_info),
                    contentDescription = null,
                    tint = Colors.darkGrey,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            SegmentedButton(
                items = stringArrayResource(Res.string.submit_spot_difficulties).toList(),
                onItemSelection = { screenModel.onDifficultySelected(it) },
                defaultSelectedItemIndex = state.selectedAccessibility,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.submit_spot_area_title),
                    style = h3(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(16.dp)
                        .clickable { screenModel.onAreaInfoClick() },
                    painter = painterResource(Res.drawable.ic_info),
                    contentDescription = null,
                    tint = Colors.darkGrey,
                )
            }
            SimpleSwitch(
                modifier = Modifier.align(Alignment.Start),
                checked = state.isArea,
                onCheckedChange = { screenModel.onAreaSwitchChange(it) },
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.submit_spot_location_picker_topbar_title),
                style = h3(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))

            val location = state.locationPickerState.selectedLocation
                ?: state.locationPickerState.currentUserLocation

            location?.let {
                Box(
                    modifier = Modifier.clickable { navigator.push(LocationPickerScreen(it)) },
                ) {
                    LockedMap(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .aspectRatio(2f)
                            .clickable { navigator.push(LocationPickerScreen(it)) },
                        cameraPosition = CameraPosition(
                            target = location.toLatLong(),
                            zoom = 15f
                        ),
                    )
                    Image(
                        painter = painterResource(Res.drawable.ic_marker),
                        contentDescription = null,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                    Icon(
                        painter = painterResource(Res.drawable.ic_marker_button),
                        contentDescription = null,
                        modifier = Modifier.align(
                            Alignment.BottomEnd
                        ).clickable {
                            navigator.push(LocationPickerScreen(it))
                        }.padding(16.dp),
                        tint = Color.Unspecified
                    )
                }
            }
            PrimaryButton(
                modifier = Modifier.padding(top = 24.dp).padding(bottom = 48.dp).fillMaxWidth(),
                onClick = { screenModel.onSubmitClick() },
                text = stringResource(Res.string.submit_spot_button_submit),
                isLoading = state.isLoading,
            )
        }
    }
}