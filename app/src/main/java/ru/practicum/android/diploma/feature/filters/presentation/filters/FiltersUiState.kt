package ru.practicum.android.diploma.feature.filters.presentation.filters

import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocation

data class FiltersUiState(
    val workLocation: WorkLocation = WorkLocation(),
    val industry: FilterIndustry? = null,
    val salaryText: String = "",
    val isCheckBox: Boolean = false,
    val errorMessage: String = ""
) {
    val hasActiveFilters: Boolean
        get() = !workLocation.isEmpty || industry != null || salaryText.isNotEmpty() || isCheckBox
}
