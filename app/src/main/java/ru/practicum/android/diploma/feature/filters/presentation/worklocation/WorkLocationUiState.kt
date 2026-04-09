package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
) {
    val locationString: String?
        get() = when {
            country != null && region != null -> "${country.name}, ${region.name}"
            country != null -> country.name
            else -> null
        }

    val isEmpty: Boolean
        get() = country == null && region == null

    companion object {
        fun fromFiltersState(filtersState: FiltersUiState): WorkLocationUiState {
            return WorkLocationUiState(
                country = filtersState.currentCountry,
                region = filtersState.currentRegion
            )
        }
    }
}
