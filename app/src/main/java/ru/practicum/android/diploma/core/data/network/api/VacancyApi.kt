package ru.practicum.android.diploma.core.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.core.data.network.dto.FilterAreaDto
import ru.practicum.android.diploma.core.data.network.dto.FilterIndustryDto
import ru.practicum.android.diploma.core.data.network.dto.VacancyDetailDto
import ru.practicum.android.diploma.core.data.network.dto.VacancyResponseDto

interface VacancyApi {
    /**
     * Получение региона (экран фильтрации)
     */
    @GET("areas")
    suspend fun getAreas(): List<FilterAreaDto>

    /**
     * Получение отрасли (экран выбора отрасли)
     */
    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustryDto>

    /**
     * Получение вакансий (экран поиска)
     *
     * @param params Набор параметров для поиска
     */
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String> = emptyMap()
    ): VacancyResponseDto

    /**
     * Получение описания вакансии
     */
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String
    ): VacancyDetailDto
}
