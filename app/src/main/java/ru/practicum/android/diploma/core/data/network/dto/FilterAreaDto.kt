package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FilterAreaDto(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<FilterAreaDto>
)

fun FilterAreaDto.toGeoArea(): GeoArea = when {
    parentId == null -> GeoArea.Country(
        id = id.toInt(),
        name = name,
        regions = areas.map { it.toGeoArea() as GeoArea.Region }
    )

    else -> GeoArea.Region(
        id = id.toInt(),
        name = name,
        countryId = parentId.toInt()
    )
}
