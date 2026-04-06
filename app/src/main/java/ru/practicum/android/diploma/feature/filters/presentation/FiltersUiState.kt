package ru.practicum.android.diploma.feature.filters.presentation

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
    val searchText: String = "",
    val isStartSearch: Boolean = false,
    val countries: List<GeoArea.Country> = emptyList()
)

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

/**
 * Определяет цель очистки данных.
 */
sealed interface ClearTarget {
    data object AppPreferences : ClearTarget
    data object All : ClearTarget
    data object Industry : ClearTarget
    data object WorkLocation : ClearTarget
    data object Country : ClearTarget
    data object Region : ClearTarget
}
