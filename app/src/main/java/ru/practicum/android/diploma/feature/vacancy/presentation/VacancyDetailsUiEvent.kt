package ru.practicum.android.diploma.feature.vacancy.presentation

/**
 * класс для работы с одноразовыми событиями
 */
sealed class VacancyDetailsUiEvent {

    data class ShareVacancyLink(val url: String) : VacancyDetailsUiEvent()

    data class OpenEmailTo(val email: String) : VacancyDetailsUiEvent()

    data class OpenPhoneCall(val phone: String) : VacancyDetailsUiEvent()
}
