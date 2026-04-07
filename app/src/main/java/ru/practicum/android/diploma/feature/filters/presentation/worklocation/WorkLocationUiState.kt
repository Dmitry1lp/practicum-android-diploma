package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import ru.practicum.android.diploma.core.domain.model.GeoArea

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
) {
    val locationString: String? = when {
        country != null && region != null -> "${country.name}, ${region.name}"
        country != null -> country.name
        else -> null
    }
}
