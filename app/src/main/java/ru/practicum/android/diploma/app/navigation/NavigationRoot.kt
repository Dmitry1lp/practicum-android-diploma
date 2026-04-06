@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.app.navigation

import android.content.Intent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.app.navigation.entries.topLevelEntryProvider
import ru.practicum.android.diploma.app.navigation.routes.BottomNavItem
import ru.practicum.android.diploma.app.navigation.routes.Route

private val bottomNavItems = listOf<BottomNavItem>(
    Route.Search,
    Route.Favorites,
    Route.Team
)

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = koinViewModel(),
) {
    val topLevelBackStack = navigationViewModel.backStack
    val entryProvider = remember(topLevelBackStack) {
        topLevelEntryProvider(topLevelBackStack)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(),
        bottomBar = {
            if (topLevelBackStack.shouldDrawBottomNavBar()) {
                BottomNavigationBar(
                    bottomNavItems = bottomNavItems,
                    topLevelBackStack = topLevelBackStack
                )
            }
        }
    ) { innerPaddings ->
        NavDisplay(
            modifier = modifier.padding(innerPaddings),
            backStack = topLevelBackStack.backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider,
            transitionSpec = {
                NavigationTransitions.slideInHorizontally(rightToLeft = true) togetherWith
                    NavigationTransitions.slideOutHorizontally(rightToLeft = false)
            },
            popTransitionSpec = {
                NavigationTransitions.slideInHorizontally(rightToLeft = false) togetherWith
                    NavigationTransitions.slideOutHorizontally(rightToLeft = true)
            },
            predictivePopTransitionSpec = {
                NavigationTransitions.slideInHorizontally(rightToLeft = false) togetherWith
                    NavigationTransitions.slideOutHorizontally(rightToLeft = true)
            }
        )
    }
}

private fun TopLevelBackStack<NavKey>.shouldDrawBottomNavBar(): Boolean =
    this.backStack.lastOrNull() is BottomNavItem
