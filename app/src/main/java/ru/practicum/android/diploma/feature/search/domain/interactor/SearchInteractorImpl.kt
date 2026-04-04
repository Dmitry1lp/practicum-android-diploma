package ru.practicum.android.diploma.feature.search.domain.interactor

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.data.models.Resource
import ru.practicum.android.diploma.feature.search.domain.repository.SearchRepository

class SearchInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchInteractor {

    override suspend fun searchVacancies(query: VacancyQuery): Resource<Triple<List<Vacancy>, Int, Int>> {
        return searchRepository.searchVacancies(query)
    }
}
