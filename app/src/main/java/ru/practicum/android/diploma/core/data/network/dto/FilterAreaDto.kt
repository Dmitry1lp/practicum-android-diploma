package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterArea

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
) {
    fun FilterAreaDto.toDomain(): FilterArea {
        return FilterArea(
            id = id,
            name = name
        )
    }
}

