package ru.practicum.android.diploma.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.search.data.models.Resource
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository

class SearchViewModel(
    private val vacancyRepository: VacancyRepository
) : ViewModel() {
    private var searchJob: Job? = null
    private var latestSearchText: String = ""

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private suspend fun performSearch(queryText: String) {
        if (queryText.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                vacancyState = VacancyState.Empty,
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            vacancyState = VacancyState.Loading,
        )

        val result = vacancyRepository.searchVacancies(
            VacancyQuery(text = queryText)
        )

        val newState = when (result) {
            is Resource.Success -> if (result.data.isEmpty()) VacancyState.Empty else VacancyState.Content(result.data)
            is Resource.Error -> VacancyState.ErrorFound
        }

        _uiState.value = _uiState.value.copy(
            vacancyState = newState,
        )
    }

    // функция которая будет использоваться при изменении текста чтобы не было конфликтов запросов
    fun onSearchTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            searchText = text,
            isClearTextVisible = text.isNotEmpty()
        )

        // дебаунс
        if (latestSearchText == text) return
        latestSearchText = text
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (text.isNotEmpty()) {
                delay(SEARCH_DEBOUNCE_DELAY)
                performSearch(text)
            } else {
                _uiState.value = _uiState.value.copy(
                    vacancyState = VacancyState.Empty,
                )
            }
        }
    }

    // очистка текста
    fun onClearTextClicked() {
        searchJob?.cancel()
        latestSearchText = ""
        _uiState.value = SearchUiState()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
