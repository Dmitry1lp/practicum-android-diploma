package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.core.domain.model.Vacancy

sealed class VacancyUiState {

    object Loading : VacancyUiState()

    data class Content(
        val vacancy: Vacancy,
        val isFavorite: Boolean
    ) : VacancyUiState()

    object NotFound : VacancyUiState()

    object NetworkError : VacancyUiState()

    object ServerError : VacancyUiState()
}
