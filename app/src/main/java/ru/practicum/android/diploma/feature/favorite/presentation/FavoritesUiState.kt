package ru.practicum.android.diploma.feature.favorite.presentation

import ru.practicum.android.diploma.core.domain.model.Vacancy

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data object Empty : FavoritesUiState
    data object FetchError : FavoritesUiState
    data class Content(val vacancies: List<Vacancy>) : FavoritesUiState
}
