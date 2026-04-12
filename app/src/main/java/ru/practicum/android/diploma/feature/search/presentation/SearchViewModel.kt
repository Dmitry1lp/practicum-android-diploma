package ru.practicum.android.diploma.feature.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.model.VacancyQuery
import ru.practicum.android.diploma.feature.filters.domain.interactor.FiltersInteractor
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractor

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

    private val _events = MutableSharedFlow<SearchEvent>()
    val events = _events.asSharedFlow()

    fun getFiltersSettings() {
        val filtersSettings = filtersInteractor.getFiltersSettings()
        filtersSettings?.let {
            _uiState.update { it.copy(filtersSettings = filtersSettings) }
            applyFiltersSettings()
        } ?: _uiState.update { it.copy(filtersSettings = null) }
    }

    private fun applyFiltersSettings() {
        val currentText = uiState.value.searchText
        val isStartSearch = uiState.value.filtersSettings?.isStartSearch
        if (currentText.isNotEmpty() && isStartSearch == true) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                performSearch(currentText)
            }
            filtersInteractor.saveFiltersSetting(
                uiState.value.filtersSettings!!.copy(
                    isStartSearch = false
                )
            )
        }
    }

    fun startSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            performSearch(uiState.value.searchText)
        }
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

        val query = applyFiltersToQuery(queryText)

        Log.d("PAGINATION0", "Requesting page = $currentPage")

        val result = searchInteractor.searchVacancies(query)

        result.fold(
            onSuccess = { (vacancies, totalPages, found) ->

                loadedVacancies.addAll(vacancies)
                maxPages = totalPages
                totalFound = found

                val newState =
                    if (vacancies.isEmpty()) VacancyState.Empty else VacancyState.Content(loadedVacancies.toList())
                _uiState.value = _uiState.value.copy(
                    vacancyState = newState,
                    totalFound = totalFound
                )
            },
            onFailure = { error ->

                val code = error.message?.toIntOrNull()

                val newState = when (code) {
                    -1 -> VacancyState.ErrorInternet
                    else -> VacancyState.ErrorFound
                }

                _uiState.value = _uiState.value.copy(
                    vacancyState = newState
                )
            }
        )
    }

    // функция, которая будет использоваться при изменении текста чтобы не было конфликтов запросов
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

    private fun applyFiltersToQuery(query: String): VacancyQuery = _uiState.value.filtersSettings.let { filters ->
        VacancyQuery(
            text = query,
            area = filters?.region?.id,
            industry = filters?.industry?.id,
            salary = filters?.salaryText?.toIntOrNull(),
            onlyWithSalary = filters?.onlyWithSalary,
            page = currentPage
        )
    }

    fun loadNextPage() {
        val queryText = _uiState.value.searchText

        if (queryText.isEmpty()) return
        if (_uiState.value.isNextPageLoading || currentPage >= maxPages) return

        viewModelScope.launch {
            _uiState.update { it.copy(isNextPageLoading = true) }

            currentPage++

            val query = applyFiltersToQuery(queryText)

            Log.d("PAGINATION", "Requesting page = $currentPage")

            val result = searchInteractor.searchVacancies(query)

            result.fold(
                onSuccess = { (vacancies, totalPages, _) ->

                    loadedVacancies.addAll(vacancies)

                    _uiState.update {
                        it.copy(
                            vacancyState = VacancyState.Content(loadedVacancies.toList())
                        )
                    }

                    maxPages = totalPages
                },
                onFailure = { error ->

                    val code = error.message?.toIntOrNull()

                    val event = when (code) {
                        -1 -> SearchEvent.ShowInternetError
                        else -> SearchEvent.ShowCommonError
                    }

                    viewModelScope.launch {
                        _events.emit(event)
                    }

                }
            )
            _uiState.update { it.copy(isNextPageLoading = false) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
