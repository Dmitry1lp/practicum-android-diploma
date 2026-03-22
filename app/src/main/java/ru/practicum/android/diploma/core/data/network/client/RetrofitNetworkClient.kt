package ru.practicum.android.diploma.core.data.network.client

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.dto.AreasRequest
import ru.practicum.android.diploma.core.data.network.dto.AreasResponse
import ru.practicum.android.diploma.core.data.network.dto.IndustriesRequest
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.core.data.network.dto.VacancySearchRequest

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

                    else -> Response().apply { resultCode = RESULT_BAD_REQUEST }
                }
                response.apply { resultCode = RESULT_OK }
            } catch (e: Throwable) {
                Log.e("RetrofitNetworkClient", "Network error", e)
                Response().apply { resultCode = RESULT_SERVER_ERROR }
            }
        }
    }

    companion object {
        private const val RESULT_OK = 200
        private const val RESULT_BAD_REQUEST = 400
        private const val RESULT_SERVER_ERROR = 500
    }
}
