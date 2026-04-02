package ru.practicum.android.diploma.feature.vacancy.domain

import ru.practicum.android.diploma.core.domain.model.Vacancy

class VacancyDetailsInteractor(
    private val repository: VacancyDetailsRepository
) {
    /**
     * загрузка вакансии
     */
    suspend fun loadVacancy(id: String): VacancyDetailsResult {
        return repository.getVacancy(id)
    }

    /**
     * избиратель для Избранного
     */
    suspend fun toggleFavorite(vacancy: Vacancy, isFavorite: Boolean) {
        if (isFavorite) {
            repository.removeFromFavorites(vacancy.id)
        } else {
            repository.addToFavourites(vacancy)
        }
    }

    /**
     * статус вакансии (избрана или нет)
     */
    suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }
}
