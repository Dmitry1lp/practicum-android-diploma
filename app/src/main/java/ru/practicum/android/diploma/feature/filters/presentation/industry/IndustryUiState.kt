package ru.practicum.android.diploma.feature.filters.presentation.industry

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

sealed interface IndustryUiState {
    data object Loading : IndustryUiState
    data object FetchError : IndustryUiState
    data class Content(
        val industries: List<FilterIndustry> = immutableListOf(),
        val filteredIndustries: List<FilterIndustry> = immutableListOf()
    ) : IndustryUiState
}
