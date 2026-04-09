package ru.practicum.android.diploma.feature.filters.presentation.region

data class RegionScreenState(
    val searchText: String = "",
    val uiState: RegionUiState = RegionUiState.Loading
)
