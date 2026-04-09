@file:Suppress("ForbiddenComment", "LongMethod")

package ru.practicum.android.diploma.app.navigation.entries

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import ru.practicum.android.diploma.app.navigation.routes.FiltersRoute
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryActions
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionActions
import ru.practicum.android.diploma.feature.filters.presentation.viewmodel.FiltersViewModel
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationActions
import ru.practicum.android.diploma.feature.filters.ui.country.SelectCountryScreen
import ru.practicum.android.diploma.feature.filters.ui.filters.FiltersScreen
import ru.practicum.android.diploma.feature.filters.ui.industry.SelectIndustryScreen
import ru.practicum.android.diploma.feature.filters.ui.region.SelectRegionScreen
import ru.practicum.android.diploma.feature.filters.ui.worklocation.WorkLocationScreen

fun filtersEntryProvider(
    viewModel: FiltersViewModel,
    backStack: NavBackStack<NavKey>,
    onCloseFilters: () -> Unit
) = entryProvider<NavKey> {
    entry<FiltersRoute.Main> {
        val state by viewModel.filtersUiState.collectAsStateWithLifecycle()
        val areButtonsEnabled = remember(state) {
            state.hasActiveFilters
        }

        FiltersScreen(
            currentState = state,
            areButtonsEnabled = areButtonsEnabled,
            actions = FiltersActions(
                onBackClick = {
                    viewModel.saveSettings(false)
                    onCloseFilters()
                },
                onWorkLocationClick = { backStack.add(FiltersRoute.WorkLocation) },
                onIndustryClick = { backStack.add(FiltersRoute.Industry) },
                onSalaryTextChange = { viewModel.onSalaryTextChange(it) },
                onCheckBoxChange = viewModel::onCheckBox,
                onApplyClick = { isStartSearch ->
                    viewModel.saveSettings(isStartSearch as Boolean)
                    onCloseFilters()
                },
                onClearClick = { target -> viewModel.clear(target) }
            )
        )
    }

    entry<FiltersRoute.Industry> {
        LaunchedEffect(Unit) { viewModel.getIndustries() }

        val state by viewModel.industryState.collectAsStateWithLifecycle()

        SelectIndustryScreen(
            screenState = state,
            actions = IndustryActions(
                onBackClick = { backStack.removeLastOrNull() },
                onSearchTextChanged = viewModel::onSearchIndustryTextChange,
                onIndustryClick = viewModel::onIndustrySelected,
                onApplyClick = { industry ->
                    viewModel.onIndustryApplied(industry)
                    backStack.removeLastOrNull()
                }
            )
        )
    }

    entry<FiltersRoute.WorkLocation> {
        val filtersUiState by viewModel.filtersUiState.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) { viewModel.getAreas() }

        val currentState by viewModel.workLocationState.collectAsStateWithLifecycle()

        WorkLocationScreen(
            currentState = currentState,
            actions = WorkLocationActions(
                onBackClick = { backStack.removeLastOrNull() },
                onCountryClick = { backStack.add(FiltersRoute.Country) },
                onRegionClick = { backStack.add(FiltersRoute.Region) },
                onClearClick = { clear -> viewModel.clear(clear) },
                onApplyClick = { state ->
                    viewModel.updateState(state)
                    backStack.removeLastOrNull()
                }
            )
        )
    }

    entry<FiltersRoute.Country> {
        val state by viewModel.countryState.collectAsStateWithLifecycle()

        SelectCountryScreen(
            onBackClick = { backStack.removeLastOrNull() },
            state = state,
            onCountryClick = { country ->
                viewModel.updateState(country)
                backStack.removeLastOrNull()
            }
        )
    }

    entry<FiltersRoute.Region> {
        val state by viewModel.regionState.collectAsStateWithLifecycle()

        SelectRegionScreen(
            screenState = state,
            actions = RegionActions(
                onRegionClick = { region ->
                    viewModel.onRegionApplied(region as GeoArea.Region)
                    backStack.removeLastOrNull()
                },
                onSearchTextChange = viewModel::onSearchRegionTextChange,
                onBackClick = { backStack.removeLastOrNull() }
            )
        )
    }
}
