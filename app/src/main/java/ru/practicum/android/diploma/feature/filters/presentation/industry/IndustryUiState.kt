package ru.practicum.android.diploma.feature.filters.presentation.industry

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

sealed interface IndustryUiState {
    data object Loading : IndustryUiState
    data object FetchError : IndustryUiState
    data class Content(
        val industries: List<FilterIndustry> = emptyList(),
        val filteredIndustries: List<FilterIndustry> = emptyList()
    ) : IndustryUiState
}
