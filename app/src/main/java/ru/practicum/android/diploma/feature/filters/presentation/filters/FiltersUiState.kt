package ru.practicum.android.diploma.feature.filters.presentation.filters

import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FiltersUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null,
    val industry: FilterIndustry? = null,
    val salaryText: String = "",
    val isCheckBox: Boolean = false,
    val errorMessage: String = "",
    val searchText: String = "",
    val countries: List<GeoArea.Country> = immutableListOf(),
    val currentCountry: GeoArea.Country? = null,
    val currentRegion: GeoArea.Region? = null,
    val allRegions: List<GeoArea.Region> = immutableListOf(),
    val filteredRegions: List<GeoArea.Region> = immutableListOf(),
    val industries: List<FilterIndustry> = immutableListOf(),
    val filteredIndustries: List<FilterIndustry> = immutableListOf(),
) {
    val isFiltersSettings: Boolean
        get() = country != null || region != null || industry != null || salaryText.isNotEmpty() || isCheckBox
}

data class FiltersActions(
    val onBackClick: () -> Unit,
    val onWorkLocationFilter: () -> Unit,
    val onIndustryFilter: () -> Unit,
    val onSalaryTextChange: (String) -> Unit,
    val onCheckBox: () -> Unit,
    val onSearchTextChange: (String) -> Unit,
    val onApplyClick: (Any?) -> Unit,
    val onClearClick: (ClearTarget) -> Unit
)
