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
    val onBackClick: () -> Unit = {},
    val onWorkLocationClick: () -> Unit = {},
    val onIndustryClick: () -> Unit = {},
    val onCheckBox: () -> Unit = {},
    val onTextChange: (String) -> Unit = {},
    val onApplyClick: (Any?) -> Unit = {},
    val onClearClick: (Clear) -> Unit = {}
)

sealed interface Clear {
    object WorkLocation : Clear
    object Country : Clear
    object Region : Clear
    object Industry : Clear
    object All : Clear
    object Settings : Clear
}
