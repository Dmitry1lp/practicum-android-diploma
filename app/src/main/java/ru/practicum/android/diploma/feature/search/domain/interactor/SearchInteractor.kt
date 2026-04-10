package ru.practicum.android.diploma.feature.search.domain.interactor

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery

interface SearchInteractor {
    suspend fun searchVacancies(query: VacancyQuery): Result<Triple<List<Vacancy>, Int, Int>>
}
