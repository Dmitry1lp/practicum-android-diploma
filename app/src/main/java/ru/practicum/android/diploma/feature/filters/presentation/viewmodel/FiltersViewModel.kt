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
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings
import ru.practicum.android.diploma.feature.filters.domain.interactor.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
import ru.practicum.android.diploma.feature.filters.presentation.country.CountryUiState
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState

class FiltersViewModel(private val interactor: FiltersInteractor) : ViewModel() {
    private val _filtersUiState = MutableStateFlow(FiltersUiState())
    val filtersUiState: StateFlow<FiltersUiState> = _filtersUiState.asStateFlow()
    var initialFiltersState: FiltersUiState? = null

    private val _industryState = MutableStateFlow(IndustryScreenState())
    val industryState: StateFlow<IndustryScreenState> = _industryState.asStateFlow()

    private val _workLocationState = MutableStateFlow(WorkLocationUiState())
    val workLocationState: StateFlow<WorkLocationUiState> = _workLocationState.asStateFlow()

    private val _countryState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)
    val countryState: StateFlow<CountryUiState> = _countryState.asStateFlow()

    init {
        getFiltersSettings()
        getAreas()
        viewModelScope.launch {
            filtersUiState.first().let { initialFiltersState = it.copy() }
            filtersUiState.collect { uiState ->
                _industryState.update { it.copy(selectedIndustry = uiState.industry) }
                _workLocationState.update { uiState.workLocation }
            }
        }
    }

    fun setWorkLocationScreen() {
        val country = filtersUiState.value.workLocation.country
        _filtersUiState.update {
            it.copy(
                currentCountry = country,
                currentRegion = filtersUiState.value.workLocation.region,
                filteredRegions = country?.regions ?: filtersUiState.value.allRegions
            )
        }
    }

    fun onIndustrySelected(industry: FilterIndustry?) = _industryState.update { it.copy(selectedIndustry = industry) }
    fun onIndustryApplied(industry: FilterIndustry?) = _filtersUiState.update { it.copy(industry = industry) }

    fun onSearchRegionTextChange(text: String) {
        val regions = filtersUiState.value.currentCountry?.regions ?: filtersUiState.value.allRegions
        _filtersUiState.update { currentState ->
            currentState.copy(
                filteredRegions = regions.queryFilter(text) { it.name })
        }
    }

    fun onSearchIndustryTextChange(text: String) = when (val uiState = _industryState.value.uiState) {
        is IndustryUiState.Content -> _industryState.update { currentState ->
            currentState.copy(
                uiState = uiState.copy(
                    industries = uiState.industries,
                    filteredIndustries = uiState.industries.queryFilter(text) { it.name }
                )
            )
        }

        else -> {}
    }

    fun updateState(current: Any) {
        when (current) {
            is WorkLocationUiState -> _filtersUiState.update {
                it.copy(workLocation = WorkLocationUiState(current.country, current.region))
            }

            is GeoArea.Region -> {
                if (filtersUiState.value.currentCountry == null) {
                    val country = (_countryState.value as CountryUiState.Content)
                        .countries.find { it.id == current.countryId }
                    _filtersUiState.update {
                        it.copy(
                            currentCountry = country,
                            currentRegion = current,
                            filteredRegions = country?.regions ?: immutableListOf()
                        )
                    }
                } else {
                    _filtersUiState.update { it.copy(currentRegion = current) }
                }
            }
        }
    }

    fun onCountryApplied(country: GeoArea.Country) {
        val isRegionInCountry = filtersUiState.value.currentRegion?.countryId == country.id

        _filtersUiState.update { it.copy(currentCountry = country, filteredRegions = country.regions) }
        if (!isRegionInCountry) _filtersUiState.update { it.copy(currentRegion = null) }
    }

    fun onSalaryTextChange(text: String) = _filtersUiState.update { it.copy(salaryText = text) }

    fun onCheckBox() = _filtersUiState.update { it.copy(isCheckBox = !filtersUiState.value.isCheckBox) }

    fun saveSettings(isStartSearch: Boolean) {
        if (filtersUiState.value.hasActiveFilters) {
            val selectedIndustry = industryState.value.selectedIndustry

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
                        workLocation = WorkLocationUiState(),
                        industry = null,
                        salaryText = "",
                        isCheckBox = false,
                        currentCountry = null,
                        currentRegion = null
                    )
                }
            }

            is ClearTarget.Industry -> {
                _filtersUiState.update { it.copy(industry = null) }
                _industryState.update { it.copy(selectedIndustry = null) }
            }

            is ClearTarget.Country -> {
                _filtersUiState.update {
                    it.copy(
                        currentCountry = null,
                        currentRegion = null,
                        filteredRegions = _filtersUiState.value.allRegions
                    )
                }
            }

            is ClearTarget.Region -> {
                _filtersUiState.update { it.copy(currentRegion = null) }
            }

            is ClearTarget.WorkLocation -> {
                _filtersUiState.update { it.copy(workLocation = WorkLocationUiState()) }
            }

        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = interactor.getFiltersSettings()
        filtersSettings?.let {
            _filtersUiState.update {
                it.copy(
                    workLocation = WorkLocationUiState(filtersSettings.country, filtersSettings.region),
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
                                    val industries = foundIndustries?.toImmutableList() ?: immutableListOf()
                                    IndustryUiState.Content(
                                        industries = industries,
                                        filteredIndustries = industries,
                                    )
                                }

                                else -> IndustryUiState.FetchError
                            }
                        )
                    }
                }
        }
    }

    private fun getAreas() {
        if (_countryState.value is CountryUiState.Content) return

        viewModelScope.launch {
            interactor
                .getCountries()
                .collect { (countries, errorMessage) ->
                    _countryState.update {
                        when (errorMessage) {
                            null -> CountryUiState.Content(countries ?: immutableListOf())
                            else -> CountryUiState.FetchError
                        }
                    }
                    _filtersUiState.update {
                        it.copy(
                            allRegions = countries?.flatMap { country -> country.regions }
                                ?.sortedBy { region -> region.name } ?: immutableListOf(),
                            errorMessage = errorMessage ?: ""
                        )
                    }
                }
        }
    }
}
