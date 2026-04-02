package ru.practicum.android.diploma.feature.search.domain.interactor

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.data.models.Resource

interface SearchInteractor {
    suspend fun search(query: VacancyQuery): Resource<Triple<List<Vacancy>, Int, Int>>
}
