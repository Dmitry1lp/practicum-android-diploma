package ru.practicum.android.diploma.feature.search.ui

data class SearchUiState(
    val searchText: String = "",
    val vacancyState: VacancyState = VacancyState.Empty,
    val isClearTextVisible: Boolean = false,
)
