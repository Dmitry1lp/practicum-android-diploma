package ru.practicum.android.diploma.feature.search.presentation

import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings
import ru.practicum.android.diploma.feature.search.ui.VacancyState

data class SearchUiState(
    val searchText: String = "",
    val vacancyState: VacancyState = VacancyState.Idle,
    val totalFound: Int = 0,
    val isNextPageLoading: Boolean = false,
    val filtersSettings: FiltersSettings? = null
)
