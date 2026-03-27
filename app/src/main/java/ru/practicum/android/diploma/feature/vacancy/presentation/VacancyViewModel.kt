package ru.practicum.android.diploma.feature.vacancy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyResult

class VacancyViewModel(
    private val interactor: VacancyInteractor,
    private val vacancyId: String
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyUiState>(VacancyUiState.Loading)
    val state: StateFlow<VacancyUiState> = _state.asStateFlow()

    init {
        loadVacancy()
    }

    private fun loadVacancy() {
        viewModelScope.launch {
            _state.value = VacancyUiState.Loading

            when (val result = interactor.loadVacancy(vacancyId)) {
                is VacancyResult.Success -> {
                    val isFavorite = interactor.isFavorite(vacancyId)

                    _state.value = VacancyUiState.Content(
                        vacancy = result.data,
                        isFavorite = isFavorite
                    )
                }

                VacancyResult.NotFound -> {
                    _state.value = VacancyUiState.NotFound
                }

                VacancyResult.NetworkError -> {
                    _state.value = VacancyUiState.NetworkError
                }

                is VacancyResult.ServerError -> {
                    _state.value = VacancyUiState.ServerError
                }
            }
        }
    }

    fun onFavouriteClick() {
        val currentState = _state.value

        if (currentState is VacancyUiState.Content) {
            viewModelScope.launch {
                interactor.toggleFavorite(
                    vacancy = currentState.vacancy,
                    isFavorite = currentState.isFavorite
                )

                _state.value = currentState.copy(
                    isFavorite = !currentState.isFavorite
                )
            }
        }
    }
}
