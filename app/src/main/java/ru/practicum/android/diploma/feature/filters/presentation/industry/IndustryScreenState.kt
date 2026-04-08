package ru.practicum.android.diploma.feature.filters.presentation.industry

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class IndustryScreenState(
    val uiState: IndustryUiState = IndustryUiState.Loading,
    val selectedIndustry: FilterIndustry? = null
)
