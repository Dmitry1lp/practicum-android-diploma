package ru.practicum.android.diploma.feature.search.data.repository

import android.util.Log
import ru.practicum.android.diploma.core.data.network.buildVacancyQuery
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailDto
import ru.practicum.android.diploma.core.data.network.dto.VacancyResponseDto
import ru.practicum.android.diploma.core.data.network.dto.toDomain
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {
    override suspend fun searchVacancies(query: VacancyQuery): Result<Triple<List<Vacancy>, Int, Int>> {
        val params = buildVacancyQuery(query)

        val response = networkClient.doRequest(
            Request.VacancySearchRequest(params)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val dto = response as? VacancyResponseDto
                    ?: return Result.failure(Exception(RESULT_FAIL))

                val vacancies = dto.items.map { it.toDomain() }

                Log.d("API_RESPONSE", "page = ${dto.page}, pages = ${dto.pages}")
                Log.d("API_RESPONSE", "vacancies = $vacancies")

                Result.success(
                    Triple(vacancies, dto.pages, dto.found)
                )
            }

            else -> Result.failure(
                Exception(response.resultCode.toString())
            )
        }
    }

    override suspend fun getVacancyDetails(id: String): Result<Vacancy> {
        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(id)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val dto = response as? VacancyDetailDto
                    ?: return Result.failure(Exception(RESULT_FAIL))

                Result.success(dto.toDomain())
            }

            else -> Result.failure(
                Exception(response.resultCode.toString())
            )
        }
    }

    companion object {
        private const val RESULT_OK = 200
        private const val RESULT_FAIL = "Error"
    }
}
