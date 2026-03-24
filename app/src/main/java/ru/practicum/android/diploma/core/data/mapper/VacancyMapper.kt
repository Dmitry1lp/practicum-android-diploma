package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailDto
import ru.practicum.android.diploma.core.domain.model.Vacancy

fun VacancyDetailDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        description = description ?: "",
        employerName = employer?.name ?: "",
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        currency = salary?.currency,
        city = address?.city ?: area?.name ?: "",
        skills = skills ?: emptyList(),
        url = url ?: ""
    )
}
