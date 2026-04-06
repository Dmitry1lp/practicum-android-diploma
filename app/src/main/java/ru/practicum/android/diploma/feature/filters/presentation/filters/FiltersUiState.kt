package ru.practicum.android.diploma.feature.filters.presentation.filters

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FiltersUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null,
    val industry: FilterIndustry? = null,
    val salaryText: String = "",
    val isCheckBox: Boolean = false,
    val industries: List<FilterIndustry> = immutableListOf(),
    val filteredIndustries: List<FilterIndustry> = immutableListOf(),
    val errorMessage: String = "",
    val searchIndustryText: String = "",
    val searchRegionText: String = "",
    val isStartSearch: Boolean = false,
    val countries: List<GeoArea.Country> = immutableListOf(),
    val currentCountry: GeoArea.Country? = null,
    val currentRegion: GeoArea.Region? = null,
    val allRegions: List<GeoArea.Region> = immutableListOf(),
    val filteredRegions: List<GeoArea.Region> = immutableListOf()
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
    object WorkLocation : Clear
    object Industry : Clear
    object All : Clear
    object Settings : Clear
    object Country : Clear
    object Region : Clear
}
