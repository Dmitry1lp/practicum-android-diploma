package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
) {
    companion object {
        fun fromFiltersState(filtersState: FiltersUiState): WorkLocationUiState {
            return WorkLocationUiState(
                country = filtersState.country,
                region = filtersState.region
            )
        }
    }
}

data class WorkLocationActions(
    val onBackClick: () -> Unit,
    val onCountryClick: () -> Unit,
    val onRegionClick: () -> Unit,
    val onApplyClick: () -> Unit
)
