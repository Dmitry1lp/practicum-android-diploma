package ru.practicum.android.diploma.feature.filters.presentation.country

import ru.practicum.android.diploma.core.domain.model.GeoArea

sealed interface CountryUiState {
    data object Loading : CountryUiState
    data object FetchError : CountryUiState
    data class Content(val countries: List<GeoArea.Country> = emptyList()) : CountryUiState
}
