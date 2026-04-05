package ru.practicum.android.diploma.feature.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.repository.FavoritesRepository

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {

    override suspend fun getVacancy(id: String): Vacancy? {
        return repository.getVacancy(id)
    }

    override suspend fun insert(vacancy: Vacancy) {
        repository.insert(vacancy)
    }

    override suspend fun delete(id: String) {
        repository.delete(id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }

    override fun getVacancies(): Flow<List<Vacancy>> {
        return repository.getVacancies()
    }
}
