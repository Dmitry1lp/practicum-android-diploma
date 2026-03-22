package ru.practicum.android.diploma.core.domain.repository

import ru.practicum.android.diploma.core.domain.model.Vacancy

interface VacancyRepository {

    suspend fun searchVacancies(
        text: String,
        area: Int?,
        industry: Int?,
        salary: Int?,
        page: Int?,
        onlyWithSalary: Boolean?
    ): List<Vacancy>

    suspend fun getVacancyDetails(id: String): Vacancy
}
