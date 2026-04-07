package ru.practicum.android.diploma.feature.filters.presentation.filters

import kotlinx.collections.immutable.persistentListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState

data class FiltersUiState(
    val workLocation: WorkLocationUiState = WorkLocationUiState(),
    val industry: FilterIndustry? = null,
    val salaryText: String = "",
    val isCheckBox: Boolean = false,
    val errorMessage: String = "",
    val searchText: String = "",
    val countries: List<GeoArea.Country> = persistentListOf(),
    val currentCountry: GeoArea.Country? = null,
    val currentRegion: GeoArea.Region? = null,
    val allRegions: List<GeoArea.Region> = persistentListOf(),
    val filteredRegions: List<GeoArea.Region> = persistentListOf(),
    val industries: List<FilterIndustry> = persistentListOf(),
    val filteredIndustries: List<FilterIndustry> = persistentListOf(),
) {
    val hasActiveFilters: Boolean
        get() = !workLocation.isEmpty || industry != null || salaryText.isNotEmpty() || isCheckBox
}
