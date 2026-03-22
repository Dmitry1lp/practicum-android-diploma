package ru.practicum.android.diploma.core.domain.repository

import ru.practicum.android.diploma.core.domain.model.Vacancy

interface FavoritesRepository {

    suspend fun addFavorite(vacancy: Vacancy)

    suspend fun removeFavorite(id: String)

    suspend fun getFavorites(): List<Vacancy>
}
