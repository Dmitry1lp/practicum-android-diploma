package ru.practicum.android.diploma.feature.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private var loadJob: Job? = null
    private var latestSearchText: String = ""
    private var currentPage = 1
    private var maxPages = 1
    private var totalFound = 0
    private var isLastLoadPageFailure = false
    private val loadedVacancies = mutableMapOf<String, Vacancy>()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SearchEvent>()
    val events = _events.asSharedFlow()

    fun getFiltersSettings() = filtersInteractor.getFiltersSettings()?.let { filtersSettings ->
        _uiState.update { it.copy(filtersSettings = filtersSettings) }
        applyFiltersSettings()
    } ?: _uiState.update { it.copy(filtersSettings = null) }

    private fun applyFiltersSettings() {
        if (_uiState.value.filtersSettings?.isStartSearch == true) startSearch()
    }

    fun startSearch(
        delayTimeMillis: Long? = null,
        text: String = _uiState.value.searchText
    ) = when {
        text.isBlank() -> {
            jobsCancel()
            _uiState.update { it.copy(vacancyState = VacancyState.Idle) }
        }

        latestSearchText.trim() == text.trim() -> {
            jobsCancel()
            if (delayTimeMillis == null) searchJob = viewModelScope.launch { performSearch(text) } else {}
        }

        else -> {
            jobsCancel()
            searchJob = viewModelScope.launch {
                delayTimeMillis?.let { delay(it) }
                performSearch(text)
            }
        }
    }

    private fun jobsCancel() {
        loadJob?.cancel()
        searchJob?.cancel()
    }

    private suspend fun performSearch(queryText: String) {
        latestSearchText = queryText
        _uiState.update { it.copy(vacancyState = VacancyState.Loading) }

        loadedVacancies.clear()
        currentPage = 1
        maxPages = 1

        val query = queryText.toVacancyQuery()
        val result = searchInteractor.searchVacancies(query)

        Log.d(LOG_TAG, "performSearch(): Requesting $currentPage")

        result.fold(
            onSuccess = { (vacancies, totalPages, found) ->
                loadedVacancies.putAll(vacancies.map { it.id to it })
                maxPages = totalPages
                totalFound = found

                _uiState.update {
                    val newState = when {
                        vacancies.isEmpty() -> VacancyState.Empty
                        else -> VacancyState.Content(loadedVacancies.values.toList())
                    }

                    it.copy(
                        vacancyState = newState,
                        totalFound = totalFound
                    )
                }
            },
            onFailure = { error ->
                val code = error.message?.toIntOrNull()

                _uiState.update {
                    val newState = when (code) {
                        -1 -> VacancyState.ErrorInternet
                        else -> VacancyState.ErrorFound
                    }

                    it.copy(vacancyState = newState)
                }
            }
        )
    }

    // функция, которая будет использоваться при изменении текста чтобы не было конфликтов запросов
    fun onSearchTextChanged(text: String) {
        if (text.isBlank()) latestSearchText = text
        _uiState.update { it.copy(searchText = text) }

        startSearch(
            text = text,
            delayTimeMillis = SEARCH_DEBOUNCE_DELAY
        )
    }

    fun loadNextPage() {
        if (isLastLoadPageFailure || loadJob?.isActive == true) return
        if (_uiState.value.isNextPageLoading || currentPage >= maxPages) return

        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isNextPageLoading = true) }

            currentPage++

            val query = _uiState.value.searchText.toVacancyQuery()
            val result = searchInteractor.searchVacancies(query)

            Log.d(LOG_TAG, "loadNextPage(): Loading page $currentPage")

            result.fold(
                onSuccess = { (vacancies, totalPages, _) ->
                    loadedVacancies.putAll(vacancies.map { it.id to it })
                    _uiState.update { it.copy(vacancyState = VacancyState.Content(loadedVacancies.values.toList())) }
                    maxPages = totalPages
                },
                onFailure = { error ->
                    currentPage--

                    val code = error.message?.toIntOrNull()

                    val event = when (code) {
                        -1 -> SearchEvent.ShowInternetError
                        else -> SearchEvent.ShowCommonError
                    }

                    viewModelScope.launch {
                        isLastLoadPageFailure = true
                        _events.emit(event)
                        delay(SHOW_TOAST_DELAY)
                        isLastLoadPageFailure = false
                    }

                }
            )
            _uiState.update { it.copy(isNextPageLoading = false) }
        }
    }

    private fun String.toVacancyQuery(): VacancyQuery = _uiState.value.filtersSettings.let { filters ->
        VacancyQuery(
            text = this,
            area = filters?.areaId,
            industry = filters?.industry?.id,
            salary = filters?.salary,
            onlyWithSalary = filters?.onlyWithSalary,
            page = currentPage
        )
    }

    companion object {
        private val LOG_TAG = SearchViewModel::class.simpleName.toString()
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val SHOW_TOAST_DELAY = 2500L
    }
}
