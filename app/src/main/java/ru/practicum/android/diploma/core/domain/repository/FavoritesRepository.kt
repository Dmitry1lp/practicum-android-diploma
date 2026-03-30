package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.data.FavoritesDataSource

interface FavoritesRepository : FavoritesDataSource {
    fun getVacancies(): Flow<List<Vacancy>>
}
