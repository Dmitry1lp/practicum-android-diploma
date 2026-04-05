package ru.practicum.android.diploma.feature.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.feature.favorite.domain.FavoritesInteractor

class FavoritesViewModel(
    interactor: FavoritesInteractor
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5000L
    }

    val state: StateFlow<FavoritesUiState> =
        interactor.getVacancies()
            .map { vacancies ->
                when {
                    vacancies.isEmpty() -> FavoritesUiState.Empty
                    else -> FavoritesUiState.Content(vacancies)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoritesUiState.Loading
            )

}
