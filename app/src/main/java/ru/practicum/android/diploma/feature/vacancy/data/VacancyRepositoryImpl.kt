package ru.practicum.android.diploma.feature.vacancy.data

import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailDto
import ru.practicum.android.diploma.core.data.network.dto.toDomain
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyRepository
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyResult

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favoritesDataSource: FavoritesDataSource // интерфейс заглушка от Избранных вакансий
) : VacancyRepository {

    override suspend fun getVacancy(id: String): VacancyResult {

        // получение данных из избранных
        val local = favoritesDataSource.getVacancy(id)
        if (local != null) {
            return VacancyResult.Success(local)
        }

        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(id)
        )

        // получение данных через NetworkClient с использованием VacancyResult
        return when (response.resultCode) {

            200 -> {
                val dto = response as? VacancyDetailDto
                if (dto != null) {
                    VacancyResult.Success(dto.toDomain())
                } else {
                    VacancyResult.ServerError(200)
                }
            }

            404 -> VacancyResult.NotFound

            else -> VacancyResult.NetworkError
        }
    }

    override suspend fun addToFavourites(vacancy: Vacancy) {
        favoritesDataSource.insert(vacancy)
    }

    override suspend fun removeFromFavorites(id: String) {
        favoritesDataSource.delete(id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return favoritesDataSource.isFavorite(id)
    }
}
