package ru.practicum.android.diploma.core.data.network.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.dto.AreasRequest
import ru.practicum.android.diploma.core.data.network.dto.AreasResponse
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.core.data.network.dto.VacancySearchRequest
import ru.practicum.android.diploma.core.data.network.dto.IndustriesRequest
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse


class RetrofitNetworkClient(
    private val api: VacancyApi
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response = when (dto) {
                    is VacancySearchRequest -> {
                        api.searchVacancies(dto.params)
                    }

                    is VacancyDetailsRequest -> {
                        api.getVacancyDetails(dto.id)
                    }

                    is AreasRequest -> {
                        AreasResponse(api.getAreas())
                    }

                    is IndustriesRequest -> {
                        IndustriesResponse(api.getIndustries())
                    }

                    else -> Response().apply { resultCode = 400 }
                }
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}
