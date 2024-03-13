package ui.base

import org.koin.core.annotation.Factory

@Factory
class NavigatorModel : BaseScreenModel<BottomNavigationTab>(BottomNavigationTab.Explore) {

    fun setCurrentBottomNavTab(bottomNavigationTab: BottomNavigationTab) {
        mutableState.value = bottomNavigationTab
    }

    fun resetCurrentBottomNavTab() {
        mutableState.value = BottomNavigationTab.Explore
    }
}