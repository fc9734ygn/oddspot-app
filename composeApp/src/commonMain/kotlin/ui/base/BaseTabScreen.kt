package ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.explore_bottom_navigation
import oddspot_app.composeapp.generated.resources.ic_bottom_nav_account
import oddspot_app.composeapp.generated.resources.ic_bottom_nav_explore
import oddspot_app.composeapp.generated.resources.profile_bottom_navigation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.screen.account.AccountScreen
import ui.screen.explore.ExploreScreen
import ui.util.Colors
import ui.util.footnote

abstract class BaseTabScreen : Screen {

    @Composable
    abstract fun ScreenContent(snackbarHostState: SnackbarHostState)


    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val navigator = LocalNavigator.currentOrThrow
        val navigatorModel = navigator.rememberNavigatorScreenModel { NavigatorModel() }
        val selectedTab = navigatorModel.currentBottomNavigationTab

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                BottomNavigation {
                    BottomNavItem(
                        isSelected = selectedTab == BottomNavigationTab.Explore,
                        BottomNavigationTab.Explore,
                        onClick = {
                            navigatorModel.setCurrentBottomNavTab(BottomNavigationTab.Explore)
                            navigator.replaceAll(ExploreScreen())
                        }
                    )
                    BottomNavItem(
                        isSelected = selectedTab == BottomNavigationTab.Account,
                        BottomNavigationTab.Account,
                        onClick = {
                            navigatorModel.setCurrentBottomNavTab(BottomNavigationTab.Account)
                            navigator.replaceAll(AccountScreen())
                        }
                    )
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                ScreenContent(snackbarHostState)
            }
        }
    }
}

sealed class BottomNavigationTab {
    data object Explore : BottomNavigationTab()
    data object Account : BottomNavigationTab()
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun RowScope.BottomNavItem(
    isSelected: Boolean,
    bottomNavigationTab: BottomNavigationTab,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Column {
                Icon(
                    painter = when (bottomNavigationTab) {
                        BottomNavigationTab.Explore -> painterResource(Res.drawable.ic_bottom_nav_explore)
                        BottomNavigationTab.Account -> painterResource(Res.drawable.ic_bottom_nav_account)
                    },
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        selectedContentColor = Colors.red,
        unselectedContentColor = Colors.lightGrey,
        modifier = Modifier.background(color = Colors.background).padding(top = 8.dp),
        label = {
            Text(
                text = when (bottomNavigationTab) {
                    BottomNavigationTab.Explore -> stringResource(Res.string.explore_bottom_navigation)
                    BottomNavigationTab.Account -> stringResource(Res.string.profile_bottom_navigation)
                },
                color = if (isSelected) Colors.red else Colors.lightGrey,
                style = footnote()
            )
        }
    )
}