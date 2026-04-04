package ru.practicum.android.diploma.feature.filters.presentation.country

import ru.practicum.android.diploma.core.domain.model.GeoArea

sealed interface SelectCountryUiState {
    data object FetchError : SelectCountryUiState
    data class Content(val countries: List<GeoArea.Country>) : SelectCountryUiState
}
