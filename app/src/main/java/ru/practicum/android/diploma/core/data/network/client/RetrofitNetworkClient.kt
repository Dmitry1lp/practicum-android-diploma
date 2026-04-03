package ru.practicum.android.diploma.core.data.network.client

import android.util.Log
import retrofit2.HttpException
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.dto.AreasResponse
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response
import java.io.IOException

class RetrofitNetworkClient(
    private val api: VacancyApi
) : NetworkClient {
    override suspend fun doRequest(request: Request): Response =
        try {
            handleRequest(request)
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
            Response().apply { resultCode = RESULT_NO_INTERNET }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error", e)
            Response().apply { resultCode = e.code() }
        }

    private suspend fun handleRequest(request: Request): Response {
        return when (request) {
            is Request.VacancySearchRequest -> api.searchVacancies(request.params)
            is Request.VacancyDetailsRequest -> api.getVacancyDetails(request.id)
            is Request.AreasRequest -> AreasResponse(api.getAreas())
            is Request.IndustriesRequest -> IndustriesResponse(api.getIndustries())
        }.apply { resultCode = RESULT_OK }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val RESULT_OK = 200
        private const val RESULT_SERVER_ERROR = 500
        private const val RESULT_NO_INTERNET = -1
    }
}
