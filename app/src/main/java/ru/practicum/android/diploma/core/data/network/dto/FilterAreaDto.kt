package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
)

@Deprecated(
    message = "Используйте FilterAreaDto.toGeoArea() для поддержки иерархии стран и регионов",
    replaceWith = ReplaceWith("this.toGeoArea()"),
    level = DeprecationLevel.WARNING
)
fun FilterAreaDto.toDomain(): FilterArea {
    return FilterArea(
        id = id,
        name = name
    )
}

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
