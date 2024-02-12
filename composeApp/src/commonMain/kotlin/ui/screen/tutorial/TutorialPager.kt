package ui.screen.tutorial

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homato.oddspot.MR
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialPager(
    pages: List<TutorialPageType>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(modifier = modifier, state = pagerState) { pageIndex ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            when (pages[pageIndex]) {
                TutorialPageType.FindPage -> TutorialPage(
                    title = stringResource(MR.strings.tutorial_page_find_title),
                    description = stringResource(MR.strings.tutorial_page_find_description),
                )

                TutorialPageType.SubmitPage -> TutorialPage(
                    title = stringResource(MR.strings.tutorial_page_submit_title),
                    description = stringResource(MR.strings.tutorial_page_submit_description),
                )

                TutorialPageType.ProfilePage -> TutorialPage(
                    title = stringResource(MR.strings.tutorial_page_profile_title),
                    description = stringResource(MR.strings.tutorial_page_profile_description),
                )

                TutorialPageType.WarningPage -> TutorialPage(
                    title = stringResource(MR.strings.tutorial_page_warning_title),
                    description = stringResource(MR.strings.tutorial_page_warning_description),
                )

                TutorialPageType.GdprPage -> TutorialPage(
                    title = stringResource(MR.strings.tutorial_page_gdpr_title),
                    description = stringResource(MR.strings.tutorial_page_gdpr_description)
                )
            }
        }
    }
}