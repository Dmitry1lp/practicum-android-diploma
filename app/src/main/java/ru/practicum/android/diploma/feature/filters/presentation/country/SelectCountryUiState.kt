package ru.practicum.android.diploma.feature.filters.presentation.country

import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState

sealed interface SelectCountryUiState {
    data object FetchError : SelectCountryUiState

    data class Content(val countries: List<GeoArea.Country>) : SelectCountryUiState {
        companion object {
            fun fromFiltersState(filtersState: FiltersUiState): Content {
                return Content(
                    countries = filtersState.countries
                )
            }
        }
    }
}
