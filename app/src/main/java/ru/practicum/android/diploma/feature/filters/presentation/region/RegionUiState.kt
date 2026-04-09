package ru.practicum.android.diploma.feature.filters.presentation.region

import ru.practicum.android.diploma.core.domain.model.GeoArea

sealed interface RegionUiState {
    data object Loading : RegionUiState
    data object FetchError : RegionUiState
    data object NoResult : RegionUiState
    data class Content(
        val regions: List<GeoArea.Region> = emptyList(),
        val filteredRegions: List<GeoArea.Region> = emptyList()
    ) : RegionUiState
}
