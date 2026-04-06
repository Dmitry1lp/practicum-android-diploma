@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.app.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import ru.practicum.android.diploma.app.navigation.routes.FiltersRoute
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationActions
import ru.practicum.android.diploma.feature.filters.ui.filters.FiltersScreen
import ru.practicum.android.diploma.feature.filters.ui.industry.IndustryFilterScreen
import ru.practicum.android.diploma.feature.filters.ui.worklocation.WorkLocationScreen

fun filtersEntryProvider(
    viewModel: FiltersViewModel,
    backStack: NavBackStack<NavKey>
) = entryProvider<NavKey> {
    entry<FiltersRoute.Main> {
        FiltersScreen(
            state = viewModel.state.collectAsState().value,
            actions = FiltersActions(
                onBackClick = {
                    viewModel.saveSettings(false)
                    backStack.removeLastOrNull()
                },
                onIndustryFilter = { backStack.add(FiltersRoute.Industry) },
                onSalaryTextChange = { viewModel.onSalaryTextChange(it) },
                onCheckBox = viewModel::onCheckBox,
                onApplyClick = { isStartSearch ->
                    viewModel.saveSettings(isStartSearch as Boolean)
                    backStack.removeLastOrNull()
                },
                onClearClick = { clear -> viewModel.clear(clear) },
                onWorkLocationFilter = { backStack.add(FiltersRoute.WorkLocation) },
                onSearchTextChange = { }
            )
        )
    }

    entry<FiltersRoute.Industry> {
        IndustryFilterScreen(
            state = viewModel.state.collectAsState().value,
            actions = FiltersActions(
                onBackClick = { backStack.removeLastOrNull() },
                onSearchTextChange = viewModel::onSearchTextChange,
                onApplyClick = { industry ->
                    viewModel.onIndustrySelected(industry as FilterIndustry)
                    backStack.removeLastOrNull()
                },
                onWorkLocationFilter = { },
                onIndustryFilter = { },
                onSalaryTextChange = { },
                onCheckBox = { },
                onClearClick = { }
            )
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
