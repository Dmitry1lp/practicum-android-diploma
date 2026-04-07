package ru.practicum.android.diploma.feature.filters.presentation.industry

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class IndustryActions(
    val onBackClick: () -> Unit,
    val onSearchTextChanged: (String) -> Unit,
    val onIndustryClick: (FilterIndustry) -> Unit,
    val onApplyClick: (FilterIndustry?) -> Unit
)
