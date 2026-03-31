package ru.practicum.android.diploma.feature.search.data.repository

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
    override suspend fun searchVacancies(query: VacancyQuery): Resource<List<Vacancy>> {
        val params = buildVacancyQuery(query)

        val response = networkClient.doRequest(
            Request.VacancySearchRequest(params)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val vacancyResponse = response as? VacancyResponseDto
                    ?: return Resource.Error(RESULT_FAIL)

                val vacancies = vacancyResponse.items.map { it.toDomain() }

                if (vacancies.isEmpty()) {
                    Resource.Error(RESULT_FAIL)
                } else {
                    Resource.Success(vacancies)
                }
            }

            else -> Resource.Error(RESULT_FAIL)
        }
    }

    override suspend fun getVacancyDetails(id: String): Resource<Vacancy> {
        val response = networkClient.doRequest(
            Request.VacancyDetailsRequest(id)
        )

        return when (response.resultCode) {
            RESULT_OK -> {
                val dto = response as? VacancyDetailDto
                    ?: return Resource.Error(RESULT_FAIL)

                Resource.Success(dto.toDomain())
            }

            else -> Resource.Error(RESULT_FAIL)
        }
    }

    companion object {
        private const val RESULT_OK = 200

        private const val RESULT_FAIL = "Error"
    }
}
