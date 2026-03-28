package ru.practicum.android.diploma.feature.vacancy.domain

import ru.practicum.android.diploma.core.domain.model.Vacancy

interface VacancyRepository {

    /**
     * функция для получения вакансии по Api
     */
    suspend fun getVacancy(id: String): VacancyResult

    /**
     * функция для добавления вакансии в Избранные (далее реализация в Фиче Избранные)
     */
    suspend fun addToFavourites(vacancy: Vacancy)

    /**
     * функция для удаления вакансии из Избранных (далее реализация в Фиче Избранные)
     */
    suspend fun removeFromFavorites(id: String)

    /**
     * функция для отслеживания статуса Вакансии на экране Вакансия
     */
    suspend fun isFavorite(id: String): Boolean

}
