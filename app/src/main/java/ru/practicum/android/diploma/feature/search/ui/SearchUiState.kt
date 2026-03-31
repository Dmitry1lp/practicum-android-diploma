package ru.practicum.android.diploma.feature.search.ui

data class SearchUiState(
    val searchText: String = "",
    val vacancyState: VacancyState = VacancyState.Idle,
    val isClearTextVisible: Boolean = false,
    val totalFound: Int = 0,
    val isNextPageLoading: Boolean = false
)
