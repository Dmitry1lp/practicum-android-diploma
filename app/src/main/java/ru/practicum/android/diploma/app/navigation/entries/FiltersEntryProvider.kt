@file:Suppress("ForbiddenComment", "LongMethod")

package ru.practicum.android.diploma.app.navigation.entries

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import ru.practicum.android.diploma.app.navigation.routes.FiltersRoute
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryActions
import ru.practicum.android.diploma.feature.filters.presentation.viewmodel.FiltersViewModel
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationActions
import ru.practicum.android.diploma.feature.filters.ui.filters.FiltersScreen
import ru.practicum.android.diploma.feature.filters.ui.industry.SelectIndustryScreen
import ru.practicum.android.diploma.feature.filters.ui.worklocation.WorkLocationScreen

fun filtersEntryProvider(
    viewModel: FiltersViewModel,
    backStack: NavBackStack<NavKey>,
    onCloseFilters: () -> Unit
) = entryProvider<NavKey> {
    entry<FiltersRoute.Main> {
        val currentState by viewModel.filtersUiState.collectAsState()
        val initState = viewModel.initialFiltersState ?: currentState

        FiltersScreen(
            currentState = currentState,
            initState = initState,
            actions = FiltersActions(
                onBackClick = onCloseFilters,
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

        val state by viewModel.industryState.collectAsState()
        val initSelectedIndustry = viewModel.filtersUiState.collectAsState().value.industry

        SelectIndustryScreen(
            screenState = state,
            initSelectedIndustry = initSelectedIndustry,
            actions = IndustryActions(
                onBackClick = { backStack.removeLastOrNull() },
                onSearchTextChanged = viewModel::onSearchTextChange,
                onIndustryClick = viewModel::onIndustrySelected,
                onApplyClick = { industry ->
                    viewModel.onIndustryApplied(industry)
                    backStack.removeLastOrNull()
                }
            )
        )
    }

    entry<FiltersRoute.WorkLocation> {
        val currentState by viewModel.workLocationState.collectAsState()
        val initState = viewModel.initialFiltersState?.workLocation ?: currentState

        WorkLocationScreen(
            currentState = currentState,
            initState = initState,
            actions = WorkLocationActions(
                onBackClick = { backStack.removeLastOrNull() },
                onCountryClick = { TODO() },
                onRegionClick = { TODO() },
                onApplyClick = { TODO() }
            )

        )
    }

    entry<FiltersRoute.Country> {
        // TODO: Выбор страны
    }

    entry<FiltersRoute.Region> {
        // TODO: Выбор региона
    }
}
