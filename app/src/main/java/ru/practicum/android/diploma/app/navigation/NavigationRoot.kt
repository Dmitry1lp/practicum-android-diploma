@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.app.navigation

import android.content.Intent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.app.ui.theme.AppDimensions.teamScreenPadding
import ru.practicum.android.diploma.feature.favorite.presentation.FavoritesViewModel
import ru.practicum.android.diploma.feature.favorite.ui.FavoritesScreen
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.feature.filters.ui.FiltersScreen
import ru.practicum.android.diploma.feature.search.presentation.SearchViewModel
import ru.practicum.android.diploma.feature.search.ui.SearchScreen
import ru.practicum.android.diploma.feature.team.ui.TeamScreen
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsUiEvent
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.feature.vacancy.ui.VacancyScreen

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
        val viewModel: FavoritesViewModel = koinViewModel()

        FavoritesScreen(
            state = viewModel.state.collectAsState().value,
            onVacancyClick = { vacancyId ->
                topLevelBackStack.add(Route.Vacancy(vacancyId))
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    entry<Route.Search> {
        val viewModel: SearchViewModel = koinViewModel()

        val state by viewModel.uiState.collectAsStateWithLifecycle()

        SearchScreen(
            state = state,
            onSearchTextChanged = viewModel::onSearchTextChanged,
            onVacancyClick = { vacancy ->
                topLevelBackStack.add(Route.Vacancy(vacancy.id))
            },
            onLoadNextPage = viewModel::loadNextPage,
            onFiltersClick = { topLevelBackStack.add(Route.Filters) }
        )
    }

    entry<Route.Vacancy> { route ->
        val vacancyId = route.id

        val viewModel: VacancyDetailsViewModel = koinViewModel(parameters = { parametersOf(vacancyId) })

        val state by viewModel.state.collectAsState()

        val context = LocalContext.current

        // загрузка данных
        LaunchedEffect(vacancyId) {
            viewModel.loadVacancy()
        }

        // обработка событий
        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    is VacancyDetailsUiEvent.ShareVacancyLink -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, event.url)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    }

                    is VacancyDetailsUiEvent.OpenEmailTo -> {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:${event.email}".toUri()
                        }
                        context.startActivity(intent)
                    }

                    is VacancyDetailsUiEvent.OpenPhoneCall -> {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:${event.phone}".toUri()
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }

        VacancyScreen(
            state = state,
            onBackClick = { topLevelBackStack.removeLast() },
            onFavouriteClick = viewModel::onFavouriteClick,
            onShareClick = viewModel::onShareClick,
            onPhoneClick = viewModel::onPhoneCall,
            onEmailClick = viewModel::onEmailClick
        )
    }

    entry<Route.Filters> {
        val viewModel: FiltersViewModel = koinViewModel()
        FiltersScreen(
            state = viewModel.state.collectAsState().value,
            actions = FiltersActions(
                onBackClick = { topLevelBackStack.removeLast() },
                onIndustriesScreen = viewModel::onIndustriesScreen,
                onSalaryTextChange = { viewModel.onSalaryTextChange(it) },
                onSearchTextChange = { viewModel.onSearchTextChange(it) },
                onCheckBox = viewModel::onCheckBox,
                onSaveSettings = {
                    viewModel.saveSettings()
                    topLevelBackStack.removeLast()
                },
                onClearSettings = viewModel::clearSettings
            )
        )
    }

    entry<Route.IndustryFilter> {
        // TODO: Выбор отрасли
    }

    entry<Route.WorkLocationFilter> {
        // TODO: Выбор места работы
    }

    entry<Route.RegionFilter> {
        // TODO: Выбор региона
    }

    entry<Route.CountryFilter> {
        // TODO: Выбор страны
    }

}
