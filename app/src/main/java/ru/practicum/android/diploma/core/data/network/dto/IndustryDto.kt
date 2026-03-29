package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Industry

class IndustryDto(
    val id: Int,
    val name: String
)

fun IndustryDto.toDomain(): Industry {
    return Industry(
        id = id,
        name = name
    )
}
