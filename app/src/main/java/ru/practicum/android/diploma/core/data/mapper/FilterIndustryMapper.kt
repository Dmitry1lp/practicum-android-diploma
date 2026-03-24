package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.data.network.dto.FilterIndustryDto
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

fun FilterIndustryDto.toDomain(): FilterIndustry {
    return FilterIndustry(
        id = id,
        name = name
    )
}
