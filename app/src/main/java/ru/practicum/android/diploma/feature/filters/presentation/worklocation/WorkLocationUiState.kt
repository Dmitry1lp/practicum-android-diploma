package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class WorkLocationUiState(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null,
    val searchText: String = "",
    val allRegions: List<GeoArea.Region> = immutableListOf(),
    val filteredRegions: List<GeoArea.Region> = immutableListOf()
) {
    val locationString: String?
        get() = when {
            country != null && region != null -> "${country.name}, ${region.name}"
            country != null -> country.name
            else -> null
        }

    val isEmpty: Boolean
        get() = country == null && region == null
}
