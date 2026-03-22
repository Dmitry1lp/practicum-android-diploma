@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.Search)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = appEntryProvider(backStack)
    )
}

private fun appEntryProvider(
    backStack: NavBackStack<NavKey>
) = entryProvider<NavKey> {
    entry<Route.Team> {
        // TODO(feature-team): интегрировать TeamScreen
        ScreenPlaceholder(it::class.simpleName)
    }

    entry<Route.Favorites> {
        // TODO(feature-team): интегрировать FavoritesScreen и FavoritesViewModel
        /*
         * - Получение ViewModel происходит через функцию koinViewModel() внутри entry{}
         * - НЕ СОЗДАВАТЬ ViewModel внутри NavigationRoot() или внутри экранов
         * - Экраны не должны принимать в качестве аргумента ViewModel
         *      и вместо этого должны принимать в качестве аргументов
         *      все необходимые состояния state и коллбэки
         * - Для перемещения на другой экран используется backStack и метод add():
         *      например, backStack.add(Route.Search)
         * - Для перемещения назад используется backStack и метод removeLastOrNull()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> backStack.add(Route.Vacancy(id)) }
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
         * - Для перемещения на другой экран используется backStack и метод add():
         *      например, backStack.add(Route.Search)
         * - Для перемещения назад используется backStack и метод removeLastOrNull()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> backStack.add(Route.Vacancy(id)) }
         * )
         */
        ScreenPlaceholder(it::class.simpleName)
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
         * - Для перемещения на другой экран используется backStack и метод add():
         *      например, backStack.add(Route.Search)
         * - Для перемещения назад используется backStack и метод removeLastOrNull()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> backStack.add(Route.Vacancy(id)) }
         * )
         */

        ScreenPlaceholder(route::class.simpleName)
    }

    entry<Route.Filters> {
        // TODO(feature-team): интегрировать FiltersScreen и FiltersViewModel
        /*
         * - Получение ViewModel происходит через функцию koinViewModel() внутри entry{}
         * - НЕ СОЗДАВАТЬ ViewModel внутри NavigationRoot() или внутри экранов
         * - Экраны не должны принимать в качестве аргумента ViewModel
         *      и вместо этого должны принимать в качестве аргументов
         *      все необходимые состояния state и коллбэки
         * - Для перемещения на другой экран используется backStack и метод add():
         *      например, backStack.add(Route.Search)
         * - Для перемещения назад используется backStack и метод removeLastOrNull()
         *
         * Пример реализации:
         *
         * val viewmodel: SearchViewModel = koinViewModel()
         *
         * SearchScreen(
         *     state = viewmodel.state,
         *     onQueryChange = viewmodel::onQueryChange,
         *     onVacancyClick = { id -> backStack.add(Route.Vacancy(id)) }
         * )
         */
        ScreenPlaceholder(it::class.simpleName)
    }
}

@Composable
private fun ScreenPlaceholder(
    text: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "placeholder\n$text",
            fontSize = 50.sp,
            color = Color.White
        )
    }
}
