package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

class IndustryDto(
    val id: Int,
    val name: String
)

fun IndustryDto.toDomain(): FilterIndustry {
    return FilterIndustry(
        id = id,
        name = name
    )
}
