package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.filters.Clear
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
) {
    companion object {
        fun fromFiltersState(filtersState: FiltersUiState): WorkLocationUiState {
            return WorkLocationUiState(
                country = filtersState.currentCountry,
                region = filtersState.currentRegion
            )
        }
    }
}

data class WorkLocationActions(
    val onBackClick: () -> Unit,
    val onCountryClick: () -> Unit,
    val onRegionClick: () -> Unit,
    val onClearClick: (Clear) -> Unit,
    val onApplyClick: (WorkLocationUiState) -> Unit
)
) {
    val locationString: String? = when {
        country != null && region != null -> "${country.name}, ${region.name}"
        country != null -> country.name
        else -> null
    }
}
