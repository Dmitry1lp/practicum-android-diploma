package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import kotlinx.collections.immutable.persistentListOf
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.utils.queryFilter

data class WorkLocation(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null
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

data class WorkLocationScreenState(
    val status: AreasStatus = AreasStatus.Loading,
    val countries: List<GeoArea.Country> = persistentListOf(),
    val workLocation: WorkLocation = WorkLocation(),
    val regionSearchQuery: String = ""
) {
    val regions: List<GeoArea.Region>
        get() = workLocation.country?.regions ?: countries.flattenRegions()

    val filteredRegions: List<GeoArea.Region>
        get() = regions.queryFilter(regionSearchQuery) { it.name }

    val isCountriesLoaded: Boolean
        get() = status is AreasStatus.Content

    val isRegionSearchEmpty: Boolean
        get() = filteredRegions.isEmpty() && regionSearchQuery.isNotBlank()
}

sealed interface AreasStatus {
    data object Loading : AreasStatus
    data object Content : AreasStatus
    data object FetchError : AreasStatus
}

fun List<GeoArea.Country>?.flattenRegions(): List<GeoArea.Region> =
    this?.flatMap { country -> country.regions } ?: persistentListOf()
