package ru.practicum.android.diploma.feature.vacancy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyDetailsInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyDetailsResult

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor,
    private val vacancyId: String
) : ViewModel() {

    private val _state = MutableStateFlow<VacancyDetailsUiState>(VacancyDetailsUiState.Loading)
    val state: StateFlow<VacancyDetailsUiState> = _state.asStateFlow()

    /**
     * для работы с одноразовыми событиями
     */
    private val _events = MutableSharedFlow<VacancyDetailsUiEvent>()
    val events = _events.asSharedFlow()

    fun loadVacancy() {
        viewModelScope.launch {
            _state.value = VacancyDetailsUiState.Loading

            when (val result = interactor.loadVacancy(vacancyId)) {
                is VacancyDetailsResult.Success -> {
                    val isFavorite = interactor.isFavorite(vacancyId)

                    _state.value = VacancyDetailsUiState.Content(
                        vacancy = result.data,
                        isFavorite = isFavorite
                    )
                }

                VacancyDetailsResult.NotFound -> {
                    _state.value = VacancyDetailsUiState.NotFound
                }

                VacancyDetailsResult.NetworkError -> {
                    _state.value = VacancyDetailsUiState.NetworkError
                }

                is VacancyDetailsResult.ServerError -> {
                    _state.value = VacancyDetailsUiState.ServerError
                }
            }
        }
    }

    fun onFavouriteClick() {
        val currentState = _state.value

        if (currentState is VacancyDetailsUiState.Content) {
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

    /**
     * обработка кликов - Share
     */
    fun onShareClick() {
        val current = _state.value
        if (current is VacancyDetailsUiState.Content) {
            viewModelScope.launch {
                _events.emit(VacancyDetailsUiEvent.ShareVacancyLink(current.vacancy.website))
            }
        }
    }

    /**
     * обработка кликов - Email
     */
    fun onEmailClick(email: String?) {
        if (email.isNullOrEmpty()) return

        viewModelScope.launch {
            _events.emit(VacancyDetailsUiEvent.OpenEmailTo(email))
        }
    }

    /**
     *  // обработка кликов - Phone
     */
    fun onPhoneCall(phone: String?) {
        if (phone.isNullOrEmpty()) return

        viewModelScope.launch {
            _events.emit(VacancyDetailsUiEvent.OpenPhoneCall(phone))
        }
    }
}
