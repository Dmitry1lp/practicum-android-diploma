package ru.practicum.android.diploma.feature.search.presentation

sealed interface SearchEvent {
    data object ShowInternetError : SearchEvent
    data object ShowCommonError : SearchEvent
}
