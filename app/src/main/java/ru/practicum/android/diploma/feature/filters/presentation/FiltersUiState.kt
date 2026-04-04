package ru.practicum.android.diploma.feature.filters.presentation

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FiltersUiState(
    val area: GeoArea? = null,
    val industry: FilterIndustry? = null,
    val salaryText: String = "",
    val onCheckBox: Boolean = false,
    val industries: List<FilterIndustry> = immutableListOf(),
    val filteredIndustries: List<FilterIndustry> = immutableListOf(),
    val errorMessage: String = "",
    val searchText: String = "",
    val isStartSearch: Boolean = false,
    val countries: List<GeoArea.Country> = emptyList()
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
    val onApplyClick: (Any?) -> Unit = {},
    val onClearClick: (Clear) -> Unit = {}
)

sealed interface Clear {
    object Industry : Clear
    object All : Clear
    object Settings : Clear
}
