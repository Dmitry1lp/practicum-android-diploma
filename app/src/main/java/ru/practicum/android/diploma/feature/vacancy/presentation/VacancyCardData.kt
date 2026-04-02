package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.core.domain.model.Vacancy

data class VacancyCardData(
    val industry: String,
    val location: String?,
    val logoUrl: String?
)

fun Vacancy.toCardData(): VacancyCardData = VacancyCardData(
    industry = industry,
    location = address?.city,
    logoUrl = employer.logoUrl
)
