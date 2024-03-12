package ui.base

import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.annotation.Factory

@Factory
class NavigatorModel : ScreenModel {
    var currentBottomNavigationTab: BottomNavigationTab = BottomNavigationTab.Explore

    fun setCurrentBottomNavTab(bottomNavigationTab: BottomNavigationTab) {
        currentBottomNavigationTab = bottomNavigationTab
    }
}