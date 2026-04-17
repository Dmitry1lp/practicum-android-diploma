package ru.practicum.android.diploma.feature.search.domain.repository

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery

interface SearchRepository {
    suspend fun searchVacancies(query: VacancyQuery): Result<Triple<List<Vacancy>, Int, Int>>
    suspend fun getVacancyDetails(id: String): Result<Vacancy>
}
