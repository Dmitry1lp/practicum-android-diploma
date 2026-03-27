package ru.practicum.android.diploma.feature.vacancy.presentation

// класс для работы с одноразовыми событиями
sealed class VacancyUiEvent {

    data class Share(val url: String) : VacancyUiEvent()

    data class OpenEmail(val email: String) : VacancyUiEvent()

    data class OpenPhone(val phone: String) : VacancyUiEvent()
}
