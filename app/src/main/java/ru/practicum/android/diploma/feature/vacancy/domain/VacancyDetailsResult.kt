package ru.practicum.android.diploma.feature.vacancy.domain

import ru.practicum.android.diploma.core.domain.model.Vacancy

/**
 * класс для обработки результата запроса через Api
 */
sealed class VacancyDetailsResult {

    data class Success(val data: Vacancy) : VacancyDetailsResult()

    object NotFound : VacancyDetailsResult()

    object NetworkError : VacancyDetailsResult()

    data class ServerError(val code: Int) : VacancyDetailsResult()
}
