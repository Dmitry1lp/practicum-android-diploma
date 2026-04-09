@file:Suppress("LongMethod")

package ru.practicum.android.diploma.app.navigation.entries

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.app.navigation.TopLevelBackStack
import ru.practicum.android.diploma.app.navigation.routes.FiltersRoute
import ru.practicum.android.diploma.app.navigation.routes.Route
import ru.practicum.android.diploma.app.ui.theme.AppDimensions.teamScreenPadding
import ru.practicum.android.diploma.feature.favorite.presentation.FavoritesViewModel
import ru.practicum.android.diploma.feature.favorite.ui.FavoritesScreen
import ru.practicum.android.diploma.feature.filters.presentation.viewmodel.FiltersViewModel
import ru.practicum.android.diploma.feature.search.presentation.SearchViewModel
import ru.practicum.android.diploma.feature.search.ui.SearchScreen
import ru.practicum.android.diploma.feature.team.presentation.TeamViewModel
import ru.practicum.android.diploma.feature.team.ui.TeamScreen
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsUiEvent
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.feature.vacancy.ui.VacancyScreen

fun topLevelEntryProvider(topLevelBackStack: TopLevelBackStack<NavKey>) = entryProvider<NavKey> {
    entry<Route.Team> {
        val viewModel: TeamViewModel = koinViewModel()
        val developers by viewModel.developers.collectAsStateWithLifecycle()

        TeamScreen(
            developers = developers,
            modifier = Modifier
                .fillMaxSize()
                .padding(teamScreenPadding)
        )
    }

    entry<Route.Favorites> {
        val viewModel: FavoritesViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        FavoritesScreen(
            state = state,
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
            onFiltersClick = { topLevelBackStack.add(Route.Filters) },
            onAction = viewModel::startSearch,
            getFiltersSettings = viewModel::getFiltersSettings
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
        val filtersViewModel: FiltersViewModel = koinViewModel()
        val filtersBackStack = rememberNavBackStack(FiltersRoute.Main)

        NavDisplay(
            backStack = filtersBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            onBack = { filtersBackStack.removeLastOrNull() },
            entryProvider = filtersEntryProvider(
                viewModel = filtersViewModel,
                backStack = filtersBackStack,
                onCloseFilters = { topLevelBackStack.removeLast() }
            )
        )
    }
}
