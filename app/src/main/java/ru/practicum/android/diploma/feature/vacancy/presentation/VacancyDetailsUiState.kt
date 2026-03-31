package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.core.domain.model.Vacancy

sealed class VacancyDetailsUiState {

    data object Loading : VacancyDetailsUiState()

    data class Content(
        val vacancy: Vacancy,
        val isFavorite: Boolean
    ) : VacancyDetailsUiState()

    data object NotFound : VacancyDetailsUiState()

    data object NetworkError : VacancyDetailsUiState()

    data object ServerError : VacancyDetailsUiState()
}
