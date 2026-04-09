package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
)

fun FilterAreaDto.toGeoArea(): GeoArea = when {
    parentId == null -> GeoArea.Country(
        id = id,
        name = name,
        regions = areas.map { it.toGeoArea() as GeoArea.Region }
    )

    else -> GeoArea.Region(
        id = id,
        name = name,
        countryId = parentId
    )
}
