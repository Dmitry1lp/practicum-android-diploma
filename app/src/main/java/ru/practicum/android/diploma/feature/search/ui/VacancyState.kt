package ru.practicum.android.diploma.feature.search.ui

import ru.practicum.android.diploma.core.domain.model.Vacancy

sealed interface VacancyState {
    data object Idle : VacancyState
    object Loading : VacancyState
    object ErrorInternet : VacancyState
    object ErrorFound : VacancyState
    object Empty : VacancyState

    data class Content(
        val vacancies: List<Vacancy>
    ) : VacancyState
}
