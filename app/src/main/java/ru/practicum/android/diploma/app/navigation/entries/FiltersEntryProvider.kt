@file:Suppress("ForbiddenComment", "LongMethod")

package ru.practicum.android.diploma.app.navigation.entries

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import ru.practicum.android.diploma.app.navigation.routes.FiltersRoute
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersViewModel
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
        val filtersUiState by viewModel.filtersUiState.collectAsState()

        FiltersScreen(
            state = filtersUiState,
            actions = FiltersActions(
                onBackClick = {
                    viewModel.saveSettings(false)
                    onCloseFilters()
                },
                onIndustryFilter = { backStack.add(FiltersRoute.Industry) },
                onSalaryTextChange = { viewModel.onSalaryTextChange(it) },
                onCheckBox = viewModel::onCheckBox,
                onApplyClick = { isStartSearch ->
                    viewModel.saveSettings(isStartSearch as Boolean)
                    onCloseFilters()
                },
                onClearClick = { clear -> viewModel.clear(clear) },
                onWorkLocationFilter = { backStack.add(FiltersRoute.WorkLocation) },
                onSearchTextChange = { }
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
            onBackClick = { backStack.removeLastOrNull() },
            onSearchTextChanged = viewModel::onSearchTextChange,
            onApplyClick = { industry ->
                viewModel.onIndustryApplied(industry)
                backStack.removeLastOrNull()
            },
            onIndustryClick = viewModel::onIndustrySelected,
        )
    }

    entry<FiltersRoute.WorkLocation> {
        val state by viewModel.workLocationState.collectAsState()
        val initState = remember(viewModel.workLocationState) {
            viewModel.workLocationState.value
        }

        WorkLocationScreen(
            currentState = state,
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
