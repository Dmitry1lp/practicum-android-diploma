package ru.practicum.android.diploma.feature.vacancy.presentation

/**
 * класс для работы с одноразовыми событиями
 */
sealed class VacancyUiEvent {

    data class ShareVacancyLink(val url: String) : VacancyUiEvent()

    data class OpenEmailTo(val email: String) : VacancyUiEvent()

    data class OpenPhoneCall(val phone: String) : VacancyUiEvent()
}
