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
import ru.practicum.android.diploma.feature.search.data.models.Resource
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {
    override suspend fun searchVacancies(query: VacancyQuery): Resource<Triple<List<Vacancy>, Int, Int>> {
        val params = buildVacancyQuery(query)

        val response = networkClient.doRequest(
            Request.VacancySearchRequest(params)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val dto = response as? VacancyResponseDto
                    ?: return Resource.Error(
                        message = RESULT_FAIL,
                        resultCode = response.resultCode
                    )

                val vacancies = dto.items.map { it.toDomain() }

                Log.d("API_RESPONSE", "page = ${dto.page}, pages = ${dto.pages}")
                Log.d("API_RESPONSE", "vacancies = $vacancies")

                Resource.Success(Triple(vacancies, dto.pages, dto.found))
            }

            else -> Resource.Error(
                message = RESULT_FAIL,
                resultCode = response.resultCode
            )
        }
    }

    override suspend fun getVacancyDetails(id: String): Resource<Vacancy> {
        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(id)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val dto = response as? VacancyDetailDto
                    ?: return Resource.Error(
                        message = RESULT_FAIL,
                        resultCode = response.resultCode
                    )

                Resource.Success(dto.toDomain())
            }

            else -> Resource.Error(
                message = RESULT_FAIL,
                resultCode = response.resultCode
            )
        }
    }

    companion object {
        private const val RESULT_OK = 200

        private const val RESULT_FAIL = "Error"
    }
}
