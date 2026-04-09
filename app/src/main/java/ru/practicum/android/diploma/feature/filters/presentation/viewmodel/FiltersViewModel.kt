@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.feature.filters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.utils.queryFilter
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings
import ru.practicum.android.diploma.feature.filters.domain.interactor.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.AreasStatus
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocation
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationScreenState

class FiltersViewModel(private val interactor: FiltersInteractor) : ViewModel() {
    private val _filtersUiState = MutableStateFlow(FiltersUiState())
    val filtersUiState: StateFlow<FiltersUiState> = _filtersUiState.asStateFlow()

    private val _industryState = MutableStateFlow(IndustryScreenState())
    val industryState: StateFlow<IndustryScreenState> = _industryState.asStateFlow()

    private val _workLocationState = MutableStateFlow(WorkLocationScreenState())
    val workLocationState: StateFlow<WorkLocationScreenState> = _workLocationState.asStateFlow()

    init {
        getFiltersSettings()
        viewModelScope.launch {
            filtersUiState.collect { uiState ->
                _industryState.update { it.copy(selectedIndustry = uiState.industry) }
            }
        }
    }

    fun onIndustrySelected(industry: FilterIndustry?) = _industryState.update { it.copy(selectedIndustry = industry) }
    fun onIndustryApplied(industry: FilterIndustry?) = _filtersUiState.update { it.copy(industry = industry) }

    fun onSearchIndustryTextChange(text: String) = when (val uiState = _industryState.value.uiState) {
        is IndustryUiState.Content -> _industryState.update { currentState ->
            currentState.copy(
                searchText = text,
                uiState = uiState.copy(
                    filteredIndustries = uiState.industries.queryFilter(text) { it.name }
                )
            )
        }

        else -> _industryState.update { it.copy(searchText = text) }
    }

    fun setWorkLocationScreen() {
        val country = filtersUiState.value.workLocation.country
        _filtersUiState.update {
            it.copy(
                searchText = "",
                workLocation = WorkLocation(country, filtersUiState.value.workLocation.region)
            )
        }
    }

    fun setIndustryScreen() = _filtersUiState.update { it.copy(searchText = "", filteredIndustries = it.industries) }

    fun onWorkLocationApplied(current: WorkLocation) = _filtersUiState.update {
        it.copy(workLocation = WorkLocation(current.country, current.region))
    }

    fun onCountryApplied(country: GeoArea.Country) = _workLocationState.update { state ->
        val isRegionValid = state.workLocation.region?.countryId == country.id

        state.copy(
            workLocation = WorkLocation(
                country = country,
                region = if (isRegionValid) state.workLocation.region else null
            )
        )
    }

    fun onRegionApplied(region: GeoArea.Region) = _workLocationState.update { state ->
        val country = state.workLocation.country ?: state.countries.find { it.id == region.countryId }

        state.copy(
            workLocation = WorkLocation(
                country = country,
                region = region
            )
        )
    }

    fun onSalaryTextChange(text: String) = _filtersUiState.update { it.copy(salaryText = text) }

    fun onSearchRegionTextChange(text: String) = _workLocationState.update { it.copy(regionSearchQuery = text) }

    fun onCheckBox() = _filtersUiState.update { it.copy(isCheckBox = !filtersUiState.value.isCheckBox) }

    fun saveSettings(isStartSearch: Boolean) {
        if (filtersUiState.value.hasActiveFilters) {
            val selectedIndustry = _industryState.value.selectedIndustry

            interactor.saveFiltersSetting(
                FiltersSettings(
                    country = filtersUiState.value.workLocation.country,
                    region = filtersUiState.value.workLocation.region,
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
            is ClearTarget.AppPreferences -> {
                interactor.clearSettings()
            }

            is ClearTarget.All -> {
                _filtersUiState.update {
                    it.copy(
                        workLocation = WorkLocation(),
                        industry = null,
                        salaryText = "",
                        isCheckBox = false
                    )
                }
                _workLocationState.update { it.copy(workLocation = WorkLocation()) }
            }

            is ClearTarget.Industry -> {
                _filtersUiState.update { it.copy(industry = null) }
                _industryState.update { it.copy(selectedIndustry = null) }
            }

            is ClearTarget.Country -> {
                _filtersUiState.update { it.copy(workLocation = WorkLocation()) }
                _workLocationState.update { it.copy(workLocation = WorkLocation()) }
            }

            is ClearTarget.Region -> {
                _filtersUiState.update { it.copy(workLocation = _filtersUiState.value.workLocation.copy(region = null)) }
                _workLocationState.update { it.copy(workLocation = _workLocationState.value.workLocation.copy(region = null)) }
            }

            is ClearTarget.WorkLocation -> {
                _filtersUiState.update { it.copy(workLocation = WorkLocation()) }
                _workLocationState.update { it.copy(workLocation = WorkLocation()) }
            }

        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = interactor.getFiltersSettings()
        filtersSettings?.let {
            _filtersUiState.update {
                it.copy(
                    workLocation = WorkLocation(filtersSettings.country, filtersSettings.region),
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
                .collect { (foundIndustries, errorMessage) ->
                    _industryState.update {
                        it.copy(
                            uiState = when (errorMessage) {
                                null -> {
                                    val industries = foundIndustries?.toImmutableList() ?: persistentListOf()
                                    IndustryUiState.Content(industries = industries, filteredIndustries = industries)
                                }

                                else -> IndustryUiState.FetchError
                            }
                        )
                    }
                }
        }
    }

    fun getAreas() {
        if (_workLocationState.value.isCountriesLoaded) return

        viewModelScope.launch {
            interactor
                .getCountries()
                .collect { (countries, errorMessage) ->
                    _workLocationState.update { uiState ->
                        Log.d("TEST", errorMessage.toString())
                        uiState.copy(
                            status = if (errorMessage == null) AreasStatus.Content else AreasStatus.FetchError,
                            countries = countries ?: persistentListOf()
                        )
                    }
                }
        }
    }
}
