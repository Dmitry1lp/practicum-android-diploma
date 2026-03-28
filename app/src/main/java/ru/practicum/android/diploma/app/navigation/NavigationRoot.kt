@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.app.ui.theme.AppDimensions.teamScreenPadding
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.feature.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.feature.filters.ui.FiltersScreen
import ru.practicum.android.diploma.feature.team.ui.TeamScreen

private val bottomNavItems = listOf<BottomNavItem>(
    Route.Search,
    Route.Favorites,
    Route.Team
)

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = koinViewModel()

) {
    val topLevelBackStack = navigationViewModel.backStack
    val entryProvider = remember(topLevelBackStack) {
        appEntryProvider(topLevelBackStack)
    }

    Scaffold(
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
            entryProvider = entryProvider
        )
    }
}

private fun TopLevelBackStack<NavKey>.shouldDrawBottomNavBar(): Boolean =
    this.backStack.lastOrNull() is BottomNavItem

private fun appEntryProvider(
    topLevelBackStack: TopLevelBackStack<NavKey>
) = entryProvider<NavKey> {
    entry<Route.Team> {
        TeamScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(teamScreenPadding)
        ) { topLevelBackStack.add(Route.Filters) }
    }

    entry<Route.Favorites> {
        // TODO(feature-team): интегрировать FavoritesScreen и FavoritesViewModel
        /*
         * - Получение ViewModel происходит через функцию koinViewModel() внутри entry{}
         * - НЕ СОЗДАВАТЬ ViewModel внутри NavigationRoot() или внутри экранов
         * - Экраны не должны принимать в качестве аргумента ViewModel
         *      и вместо этого должны принимать в качестве аргументов
         *      все необходимые состояния state и коллбэки
         * - Для перемещения на другой экран используется topLevelBackStack и метод add():
         *      например, topLevelBackStack.add(Route.Search)
         * - Для перемещения назад используется topLevelBackStack и метод removeLast()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> topLevelBackStack.add(Route.Vacancy(id)) }
         * )
         */
        ScreenPlaceholder(it::class.simpleName)
    }

    entry<Route.Search> {
        // TODO(feature-team): интегрировать SearchScreen и SearchViewModel
        /*
         * - Получение ViewModel происходит через функцию koinViewModel() внутри entry{}
         * - НЕ СОЗДАВАТЬ ViewModel внутри NavigationRoot() или внутри экранов
         * - Экраны не должны принимать в качестве аргумента ViewModel
         *      и вместо этого должны принимать в качестве аргументов
         *      все необходимые состояния state и коллбэки
         * - Для перемещения на другой экран используется topLevelBackStack и метод add():
         *      например, topLevelBackStack.add(Route.Search)
         * - Для перемещения назад используется topLevelBackStack и метод removeLast()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> topLevelBackStack.add(Route.Vacancy(id)) }
         * )
         */
        ScreenPlaceholder(it::class.simpleName) {
            topLevelBackStack.add(Route.Vacancy("Dmitrii"))
        }
    }

    entry<Route.Vacancy> { route ->
        val vacancyId = route.id

        // TODO(feature-team): интегрировать VacancyScreen и VacancyViewModel
        /*
         * - Получение ViewModel происходит через функцию koinViewModel() внутри entry{}
         * - НЕ СОЗДАВАТЬ ViewModel внутри NavigationRoot() или внутри экранов
         * - Экраны не должны принимать в качестве аргумента ViewModel
         *      и вместо этого должны принимать в качестве аргументов
         *      все необходимые состояния state и коллбэки
         * - Для перемещения на другой экран используется topLevelBackStack и метод add():
         *      например, topLevelBackStack.add(Route.Search)
         * - Для перемещения назад используется topLevelBackStack и метод removeLast()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> topLevelBackStack.add(Route.Vacancy(id)) }
         * )
         */

        ScreenPlaceholder(route::class.simpleName)
    }

    entry<Route.Filters> {
        val viewModel: FiltersViewModel = koinViewModel()
        FiltersScreen(
            stateViewModel = viewModel.state,
            onBackClick = { topLevelBackStack.removeLast() },
//            onBranchScreen = viewModel::onBranchScreen
        )
    }
}

@Composable
private fun ScreenPlaceholder(
    text: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "placeholder\n$text",
            color = Color.White,
            style = AppTypography.titleLarge
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun NavigationRootPreviewLightMode() {
    DiplomaTheme(false) {
        NavigationRoot(
            modifier = Modifier.fillMaxSize(),
            navigationViewModel = remember { NavigationViewModel() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun NavigationRootPreviewDarkMode() {
    DiplomaTheme(true) {
        NavigationRoot(
            modifier = Modifier.fillMaxSize(),
            navigationViewModel = remember { NavigationViewModel() }
        )
    }
}
