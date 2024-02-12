package ui.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.homato.oddspot.MR
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.compose.colorResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorCount: Int = 5,
    indicatorSize: Dp = 16.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 8.dp,
    activeColor: ColorResource = MR.colors.green,
    inactiveColor: ColorResource = MR.colors.grey_light,
    itemCount: Int,
) {

    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    val currentItem = remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            currentItem.value,
            (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }


    // I'm too lazy to understand this and name all the variables right now
    @Suppress("MagicNumber")
    LazyRow(
        modifier = modifier.width(totalWidth),
        state = listState,
        contentPadding = PaddingValues(vertical = space),
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically,
        userScrollEnabled = false
    ) {

        items(itemCount) { index ->

            val isSelected = (index == currentItem.value)

            // Index of item in center when odd number of indicators are set
            // for 5 indicators this is 2nd indicator place
            val centerItemIndex = indicatorCount / 2

            val right1 =
                (currentItem.value < centerItemIndex &&
                        index >= indicatorCount - 1)

            val right2 =
                (currentItem.value >= centerItemIndex &&
                        index >= currentItem.value + centerItemIndex &&
                        index <= itemCount - centerItemIndex + 1)
            val isRightEdgeItem = right1 || right2
            // Check if this item's distance to center item is smaller than half size of
            // the indicator count when current indicator at the center or
            // when we reach the end of list. End of the list only one item is on edge
            // with 10 items and 7 indicators
            // 7-3= 4th item can be the first valid left edge item and
            val isLeftEdgeItem =
                index <= currentItem.value - centerItemIndex &&
                        currentItem.value > centerItemIndex &&
                        index < itemCount - indicatorCount + 1
            val scale = if (isSelected) {
                1f
            } else if (isLeftEdgeItem || isRightEdgeItem) {
                .5f
            } else {
                .8f
            }
            Box(
                modifier = Modifier
                    .clip(indicatorShape)
                    .size(indicatorSize * scale)
                    .background(
                        if (isSelected) colorResource(activeColor) else colorResource(inactiveColor),
                        indicatorShape
                    )
                    .then(Modifier),
            )
        }
    }
}