package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterIndustry

class FilterIndustryDto(
    val id: Int,
    val name: String
)

fun FilterIndustryDto.toDomain(): FilterIndustry {
    return FilterIndustry(
        id = id,
        name = name
    )
}
