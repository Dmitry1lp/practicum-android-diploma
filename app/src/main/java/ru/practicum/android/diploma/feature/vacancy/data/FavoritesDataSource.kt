package ru.practicum.android.diploma.feature.vacancy.data

import ru.practicum.android.diploma.core.domain.model.Vacancy

/*
  интерфейс заглушка (реально реализуется в фиче Избранные)
 */
interface FavoritesDataSource {

    suspend fun getVacancy(id: String): Vacancy?

    suspend fun insert(vacancy: Vacancy)

    suspend fun delete(id: String)

    suspend fun isFavorite(id: String): Boolean
}
