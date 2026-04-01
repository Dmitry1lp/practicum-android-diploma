package ru.practicum.android.diploma.feature.search.domain.interactor

import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.data.models.Resource
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository

class SearchInteractorImpl(
    private val repository: VacancyRepository
) : SearchInteractor {

    override suspend fun search(query: VacancyQuery): Resource<Triple<List<Vacancy>, Int, Int>> {
        return repository.searchVacancies(query)
    }
}
