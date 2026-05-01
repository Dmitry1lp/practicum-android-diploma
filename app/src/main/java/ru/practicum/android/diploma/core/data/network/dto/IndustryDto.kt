package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

class IndustryDto(
    val id: String,
    val name: String
)

fun IndustryDto.toIndustry(): FilterIndustry {
    return FilterIndustry(
        id = id.toInt(),
        name = name
    )
}
