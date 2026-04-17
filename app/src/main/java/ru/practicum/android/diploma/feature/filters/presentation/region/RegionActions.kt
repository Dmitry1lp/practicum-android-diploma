package ru.practicum.android.diploma.feature.filters.presentation.region

import ru.practicum.android.diploma.core.domain.model.GeoArea

data class RegionActions(
    val onRegionClick: (GeoArea) -> Unit,
    val onSearchTextChange: (String) -> Unit,
    val onBackClick: () -> Unit
)
