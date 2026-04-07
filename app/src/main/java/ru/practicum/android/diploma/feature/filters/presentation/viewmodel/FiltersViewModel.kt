@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.feature.filters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.utils.queryFilter
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings
import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState

class FiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

    private val _filtersUiState = MutableStateFlow(FiltersUiState())
    val filtersUiState: StateFlow<FiltersUiState> = _filtersUiState.asStateFlow()
    var initialFiltersState: FiltersUiState? = null

    private val _industryState = MutableStateFlow(IndustryScreenState())
    val industryState: StateFlow<IndustryScreenState> = _industryState.asStateFlow()

    private val _workLocationState =
        MutableStateFlow(WorkLocationUiState()) // TODO: забирать актуальное состояние
    val workLocationState: StateFlow<WorkLocationUiState> = _workLocationState.asStateFlow()

    init {
        getFiltersSettings()
        viewModelScope.launch {
            filtersUiState.first().let { state ->
                initialFiltersState = state.copy()
            }
        }
    }

    fun onIndustrySelected(industry: FilterIndustry?) {
        _industryState.update { it.copy(selectedIndustry = industry) }
    }

    fun onIndustryApplied(industry: FilterIndustry?) {
        _filtersUiState.update { it.copy(industry = industry) }
    }

    fun onSearchTextChange(text: String) = when (val uiState = _industryState.value.uiState) {
        is IndustryUiState.Content -> _industryState.update { currentState ->
            currentState.copy(
                searchText = text,
                uiState = uiState.copy(
                    industries = uiState.industries,
                    filteredIndustries = uiState.industries.queryFilter(text) { it.name }
                )
            )
        }

        else -> _industryState.update { it.copy(searchText = text) }
    }

    fun onSalaryTextChange(text: String) {
        _filtersUiState.update { it.copy(salaryText = text) }
    }

    fun onCheckBox() {
        _filtersUiState.update { it.copy(isCheckBox = !filtersUiState.value.isCheckBox) }
    }

    fun saveSettings(isStartSearch: Boolean) {
        val selectedIndustry = industryState.value.selectedIndustry

        val hasActiveFilters =
            filtersUiState.value.country != null ||
                filtersUiState.value.region != null ||
                selectedIndustry != null ||
                filtersUiState.value.salaryText.isNotEmpty() ||
                filtersUiState.value.isCheckBox

        if (hasActiveFilters) {
            interactor.saveFiltersSetting(
                FiltersSettings(
                    country = filtersUiState.value.country,
                    region = filtersUiState.value.region,
                    industry = selectedIndustry,
                    salaryText = filtersUiState.value.salaryText.ifEmpty { null },
                    onlyWithSalary = filtersUiState.value.isCheckBox.let { if (!it) null else true },
                    isStartSearch = isStartSearch
                )
            )
        } else {
            clear(ClearTarget.AppPreferences)
        }
    }

    /**
     * Сбрасывает значение указанного [target].
     */
    fun clear(target: ClearTarget) {
        when (target) {
            is ClearTarget.AppPreferences -> interactor.clearSettings()
            is ClearTarget.All -> {
                _filtersUiState.update {
                    it.copy(
                        country = null,
                        region = null,
                        industry = null,
                        salaryText = "",
                        isCheckBox = false
                    )
                }
            }

            is ClearTarget.Industry -> {
                _filtersUiState.update { it.copy(industry = null) }
                _industryState.update { it.copy(selectedIndustry = null) }
            }

            is ClearTarget.Country -> TODO()
            is ClearTarget.Region -> TODO()
            is ClearTarget.WorkLocation -> TODO()
        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = interactor.getFiltersSettings()
        filtersSettings?.let {
            _filtersUiState.update {
                it.copy(
                    country = filtersSettings.country,
                    region = filtersSettings.region,
                    industry = filtersSettings.industry,
                    salaryText = filtersSettings.salaryText ?: "",
                    isCheckBox = filtersSettings.onlyWithSalary ?: false
                )
            }
        }
    }

    fun getIndustries() {
        if (_industryState.value.uiState is IndustryUiState.Content) return

        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { pair ->
                    processIndustriesResult(pair.first, pair.second)
                }
        }
    }

    private fun processIndustriesResult(
        foundIndustries: List<FilterIndustry>?,
        errorMessage: String?
    ) {
        if (errorMessage != null) {
            _industryState.update { it.copy(uiState = IndustryUiState.FetchError) }
            return
        }

        val industries = foundIndustries?.toImmutableList() ?: immutableListOf()
        _industryState.update {
            it.copy(
                uiState = IndustryUiState.Content(
                    industries = industries,
                    filteredIndustries = industries,
                )
            )
        }
    }

    private fun getAreas() {
        viewModelScope.launch {
            interactor
                .getAreas()
                .collect { pair ->
                    progressAreasResult(pair.first, pair.second)
                }
        }
    }

    private fun progressAreasResult(
        countries: List<GeoArea.Country>?,
        errorMessage: String?
    ) {
        _filtersUiState.update {
            it.copy(
                countries = countries ?: emptyList(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
