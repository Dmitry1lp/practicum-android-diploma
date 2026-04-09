package ru.practicum.android.diploma.feature.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Vacancy

interface FavoritesInteractor {

    suspend fun getVacancy(id: String): Vacancy?

    suspend fun insert(vacancy: Vacancy)

    suspend fun delete(id: String)

    suspend fun isFavorite(id: String): Boolean

    fun getVacancies(): Flow<List<Vacancy>>
}
