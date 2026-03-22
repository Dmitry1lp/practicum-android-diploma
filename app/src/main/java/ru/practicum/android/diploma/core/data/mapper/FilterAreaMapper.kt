package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.data.network.dto.FilterAreaDto
import ru.practicum.android.diploma.core.domain.model.FilterArea

fun FilterAreaDto.toDomain(): FilterArea {
    return FilterArea(
        id = id,
        name = name
    )
}
