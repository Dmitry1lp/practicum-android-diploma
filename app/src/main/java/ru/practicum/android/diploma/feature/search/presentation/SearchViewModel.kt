package ru.practicum.android.diploma.feature.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.search.data.models.Resource
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.feature.search.ui.VacancyState

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {
    private var searchJob: Job? = null
    private var latestSearchText: String = ""
    private var currentPage = 1
    private var maxPages = 1
    private var totalFound = 0
    private val loadedVacancies = mutableListOf<Vacancy>()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun loadFiltersSettings() {
        getFiltersSettings()
        val currentText = _uiState.value.searchText
        if (currentText.isNotEmpty() && uiState.value.filtersSettings?.isStartSearch == true) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                performSearch(currentText)
            }
        }
    }

    fun startSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            performSearch(uiState.value.searchText)
        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = filtersInteractor.getFiltersSettings()
        filtersSettings?.let {
            _uiState.update { it.copy(filtersSettings = filtersSettings) }
        } ?: _uiState.update { it.copy(filtersSettings = null) }
    }

    private suspend fun performSearch(queryText: String) {
        if (queryText.isEmpty()) {
            _uiState.value = _uiState.value.copy(vacancyState = VacancyState.Empty)
            return
        }

        _uiState.value = _uiState.value.copy(vacancyState = VacancyState.Loading)

        loadedVacancies.clear()
        currentPage = 1
        maxPages = 1

        val filters = _uiState.value.filtersSettings

        val query = VacancyQuery(
            text = queryText,
            area = filters?.area?.id,
            industry = filters?.industry?.id,
            salary = filters?.salaryText?.toIntOrNull(),
            onlyWithSalary = filters?.onlyWithSalary,
            page = currentPage
        )

        Log.d("PAGINATION0", "Requesting page = $currentPage")
        when (val result = searchInteractor.searchVacancies(query)) {
            is Resource.Success -> {
                val (vacancies, totalPages, found) = result.data

                loadedVacancies.addAll(vacancies)
                maxPages = totalPages
                totalFound = found

                val newState =
                    if (vacancies.isEmpty()) VacancyState.Empty else VacancyState.Content(loadedVacancies.toList())
                _uiState.value = _uiState.value.copy(
                    vacancyState = newState,
                    totalFound = totalFound
                )
            }

            is Resource.Error -> {
                val newState = when (result.resultCode) {
                    -1 -> VacancyState.ErrorInternet
                    else -> VacancyState.ErrorFound
                }

                _uiState.value = _uiState.value.copy(
                    vacancyState = newState
                )
            }
        }
    }

    // функция которая будет использоваться при изменении текста чтобы не было конфликтов запросов
    fun onSearchTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            searchText = text,
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
                    vacancyState = VacancyState.Idle,
                )
            }
        }
    }

    fun loadNextPage() {
        val queryText = _uiState.value.searchText

        if (queryText.isEmpty()) return
        if (_uiState.value.isNextPageLoading || currentPage >= maxPages) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isNextPageLoading = true)
            }

            currentPage++

            val filters = _uiState.value.filtersSettings

            val query = VacancyQuery(
                text = queryText,
                area = filters?.area?.id,
                industry = filters?.industry?.id,
                salary = filters?.salaryText?.toIntOrNull(),
                onlyWithSalary = filters?.onlyWithSalary,
                page = currentPage
            )

            Log.d("PAGINATION", "Requesting page = $currentPage")
            when (val result = searchInteractor.searchVacancies(query)) {
                is Resource.Success -> {
                    val (vacancies, totalPages) = result.data

                    loadedVacancies.addAll(vacancies)

                    _uiState.update {
                        it.copy(
                            vacancyState = VacancyState.Content(loadedVacancies.toList())
                        )
                    }

                    maxPages = totalPages
                }

                is Resource.Error -> {
                    val newState = when (result.resultCode) {
                        -1 -> VacancyState.ErrorInternet
                        else -> VacancyState.ErrorFound
                    }

                    _uiState.update {
                        it.copy(vacancyState = newState)
                    }
                }
            }
            _uiState.update {
                it.copy(isNextPageLoading = false)
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
