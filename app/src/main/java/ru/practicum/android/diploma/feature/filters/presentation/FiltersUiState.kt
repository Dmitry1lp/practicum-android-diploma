package ru.practicum.android.diploma.feature.filters.presentation

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class FiltersUiState(
    val area: FilterArea = FilterArea(0, ""),
    val industry: FilterIndustry = FilterIndustry(0, ""),
    val salaryText: String = "",
    val isCheckBox: Boolean = false,
    val industries: List<FilterIndustry> = immutableListOf(),
    val filteredIndustries: List<FilterIndustry> = immutableListOf(),
    val errorMessage: String = "",
    val searchText: String = "",
    val isStartSearch: Boolean = false
)

data class FiltersActions(
    val onBackClick: () -> Unit = {},
    val onWorkLocationFilter: () -> Unit = {},
    val onCountryFilter: () -> Unit = {},
    val onRegionFilter: () -> Unit = {},
    val onIndustryFilter: () -> Unit = {},
    val onSalaryTextChange: (String) -> Unit = {},
    val onCheckBox: () -> Unit = {},
    val onSearchTextChange: (String) -> Unit = {},
    val onActivateButton: (Any?) -> Unit = {},
    val onClearClick: (Clear) -> Unit = {}
)

sealed interface Clear {
    object Industry : Clear
    object All : Clear
}
