package ru.practicum.android.diploma.feature.filters.presentation.work_location

import ru.practicum.android.diploma.core.domain.model.GeoArea

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
)

data class WorkLocationActions(
    val onBackClick: () -> Unit,
    val onCountryClick: () -> Unit,
    val onRegionClick: () -> Unit,
    val onApplyClick: () -> Unit
)
