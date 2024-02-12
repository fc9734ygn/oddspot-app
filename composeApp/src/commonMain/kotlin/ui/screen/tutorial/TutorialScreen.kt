package ui.screen.tutorial

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.stringResource
import ui.component.pager.PagerIndicator

class TutorialScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val pages = listOf(
                TutorialPageType.FindPage,
                TutorialPageType.SubmitPage,
                TutorialPageType.ProfilePage,
                TutorialPageType.WarningPage,
                TutorialPageType.GdprPage
            )
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0.01f,
                pageCount = { pages.size }
            )
            val showButton = remember {
                derivedStateOf {
                    pagerState.currentPage == pages.size - 1
                }
            }
            TutorialPager(
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.Center)
            )
            AnimatedVisibility(
                visible = showButton.value,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp)
            ) {
                Button(
                    onClick = TODO(),
                ) {
                    Text(
                        text = stringResource(MR.strings.tutorial_finished_button)
                    )
                }
            }
            PagerIndicator(
                pagerState = pagerState, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                itemCount = pages.size,
                indicatorCount = pages.size
            )
        }
    }

}