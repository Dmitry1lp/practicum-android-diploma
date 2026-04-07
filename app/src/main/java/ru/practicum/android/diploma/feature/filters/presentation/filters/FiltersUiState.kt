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
    val isStartSearch: Boolean = false,
    val countries: List<GeoArea.Country> = emptyList()
)
