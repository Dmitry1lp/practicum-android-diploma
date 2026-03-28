package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.core.domain.model.Vacancy

sealed class VacancyUiState {

    data object Loading : VacancyUiState()

    data class Content(
        val vacancy: Vacancy,
        val isFavorite: Boolean
    ) : VacancyUiState()

    data object NotFound : VacancyUiState()

    data object NetworkError : VacancyUiState()

    data object ServerError : VacancyUiState()
}
