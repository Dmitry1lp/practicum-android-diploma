package ru.practicum.android.diploma.feature.search.presentation

sealed class SearchEvent {
    data object ShowInternetError : SearchEvent()
    data object ShowCommonError : SearchEvent()
}
