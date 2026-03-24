package ru.practicum.android.diploma.core.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.core.data.network.dto.FilterAreaDto
import ru.practicum.android.diploma.core.data.network.dto.FilterIndustryDto
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailDto
import ru.practicum.android.diploma.core.data.network.dto.VacancyResponseDto

interface VacancyApi {
    // получение региона(экран фильтрации)
    @GET("areas")
    suspend fun getAreas(): List<FilterAreaDto>

    // получение отрасли(экран выбора отрасли)
    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustryDto>

    // получение вакансии(экран поиска)
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    ): VacancyResponseDto

    // получение описания вакансии
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String
    ): VacancyDetailDto
}
