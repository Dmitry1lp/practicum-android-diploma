package ru.practicum.android.diploma.feature.vacancy.domain

import ru.practicum.android.diploma.core.domain.model.Vacancy

/**
 * класс для обработки результата запроса через Api
 */
sealed class VacancyResult {

    data class Success(val data: Vacancy) : VacancyResult()

    object NotFound : VacancyResult()

    object NetworkError : VacancyResult()

    data class ServerError(val code: Int) : VacancyResult()
}
