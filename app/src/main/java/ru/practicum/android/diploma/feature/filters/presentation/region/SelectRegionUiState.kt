package ru.practicum.android.diploma.feature.filters.presentation.region

import ru.practicum.android.diploma.core.domain.model.GeoArea

sealed interface SelectRegionUiState {
    data object FetchError : SelectRegionUiState
    data object NoSuchRegion : SelectRegionUiState
    data class Content(val regions: List<GeoArea.Region>) : SelectRegionUiState
}
