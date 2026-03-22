package ru.practicum.android.diploma.core.data.network.client

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.dto.AreasRequest
import ru.practicum.android.diploma.core.data.network.dto.AreasResponse
import ru.practicum.android.diploma.core.data.network.dto.IndustriesRequest
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.core.data.network.dto.VacancySearchRequest
import java.io.IOException

class RetrofitNetworkClient(
    private val api: VacancyApi
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response = withContext(Dispatchers.IO) {
        try {
            handleRequest(dto)
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
            Response().apply { resultCode = RESULT_SERVER_ERROR }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error", e)
            Response().apply { resultCode = e.code() }
        }
    }

    private suspend fun handleRequest(dto: Any): Response {
        return when (dto) {
            is VacancySearchRequest -> api.searchVacancies(dto.params)
            is VacancyDetailsRequest -> api.getVacancyDetails(dto.id)
            is AreasRequest -> AreasResponse(api.getAreas())
            is IndustriesRequest -> IndustriesResponse(api.getIndustries())
            else -> Response().apply { resultCode = RESULT_BAD_REQUEST }
        }.apply { resultCode = RESULT_OK }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val RESULT_OK = 200
        private const val RESULT_BAD_REQUEST = 400
        private const val RESULT_SERVER_ERROR = 500
    }
}
