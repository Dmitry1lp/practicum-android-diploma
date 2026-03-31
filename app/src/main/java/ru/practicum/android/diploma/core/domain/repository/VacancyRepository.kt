package ru.practicum.android.diploma.core.domain.repository

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery

interface VacancyRepository {

    suspend fun searchVacancies(
        query: VacancyQuery
    ): List<Vacancy>

    suspend fun getVacancyDetails(id: String): Vacancy
}
