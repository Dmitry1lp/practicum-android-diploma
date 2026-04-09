package ru.practicum.android.diploma.feature.search.domain.repository

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.data.models.Resource

interface SearchRepository {
    suspend fun searchVacancies(query: VacancyQuery): Resource<Triple<List<Vacancy>, Int, Int>>
    suspend fun getVacancyDetails(id: String): Resource<Vacancy>
}
