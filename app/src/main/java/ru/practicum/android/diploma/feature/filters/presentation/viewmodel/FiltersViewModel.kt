@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.feature.filters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionScreenState
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionUiState
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

    private val _regionState = MutableStateFlow(RegionScreenState())
    val regionState: StateFlow<RegionScreenState> = _regionState.asStateFlow()

    init {
        getFiltersSettings()
        viewModelScope.launch {
            filtersUiState.first().let { initialFiltersState = it.copy() }
            filtersUiState.collect { uiState ->
                _industryState.update { it.copy(selectedIndustry = uiState.industry) }
                _workLocationState.update { uiState.workLocation }
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
                currentCountry = country,
                currentRegion = filtersUiState.value.workLocation.region
            )
        }
    }

    fun setIndustryScreen() = _filtersUiState.update { it.copy(searchText = "", filteredIndustries = it.industries) }

    fun updateState(current: Any) {
        when (current) {
            is WorkLocationUiState -> _filtersUiState.update {
                it.copy(workLocation = WorkLocationUiState(current.country, current.region))
            }
        }
    }

    fun onCountryApplied(country: GeoArea.Country) {
        val currentRegionUiState = (_regionState.value.uiState as RegionUiState.Content)
        val isRegionInCountry = filtersUiState.value.currentRegion?.countryId == country.id

        _filtersUiState.update { it.copy(currentCountry = country) }
        _regionState.update { it.copy(uiState = currentRegionUiState.copy(filteredRegions = country.regions)) }
        if (!isRegionInCountry) _filtersUiState.update { it.copy(currentRegion = null) }
    }

    fun onIndustrySelected(industry: FilterIndustry?) = _industryState.update { it.copy(selectedIndustry = industry) }

    fun onRegionApplied(region: GeoArea.Region) {
        val isCountrySelected = filtersUiState.value.currentCountry != null

        _filtersUiState.update { it.copy(currentRegion = region) }
        if (!isCountrySelected) {
            val country = (_countryState.value as CountryUiState.Content)
                .countries
                .find { it.id == _filtersUiState.value.currentRegion?.countryId }
            _filtersUiState.update { it.copy(currentCountry = country) }
        }
    }

    fun onSalaryTextChange(text: String) = _filtersUiState.update { it.copy(salaryText = text) }

    fun onSearchRegionTextChange(text: String) = when (val uiState = _regionState.value.uiState) {
        is RegionUiState.Content -> _regionState.update { currentState ->
            currentState.copy(
                searchText = text,
                uiState = uiState.copy(
                    regions = uiState.regions,
                    filteredRegions = uiState.regions.queryFilter(text) { it.name }
                )
            )
        }

        else -> _regionState.update { it.copy(searchText = text) }
    }

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
                        workLocation = WorkLocationUiState(),
                        industry = null,
                        salaryText = "",
                        isCheckBox = false,
                    )
                }
            }

            is ClearTarget.WorkLocation -> {
                _filtersUiState.update { it.copy(workLocation = WorkLocationUiState()) }
            }

            is ClearTarget.Country -> {
                _workLocationState.update { currentState ->
                    currentState.copy(
                        country = null,
                        region = null,
                        filteredRegions = currentState.allRegions.queryFilter(currentState.searchText) { it.name }
                    )
                }
            }

            is ClearTarget.Region -> {
                _workLocationState.update { it.copy(region = null) }
            }

            is ClearTarget.Industry -> {
                _filtersUiState.update { it.copy(industry = null) }
                _industryState.update { it.copy(selectedIndustry = null) }
            }

            is ClearTarget.Country -> {
                _filtersUiState.update {
                    it.copy(
                        currentCountry = null,
                        currentRegion = null
                    )
                }
                _regionState.update {
                    val currentRegionUiState = it.uiState as RegionUiState.Content
                    it.copy(uiState = currentRegionUiState.copy(filteredRegions = _filtersUiState.value.allRegions))
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
        if (_countryState.value is CountryUiState.Content) return
        if (_regionState.value.uiState is RegionUiState.Content) return

        viewModelScope.launch {
            interactor
                .getCountries()
                .collect { (countries, errorMessage) ->
                    val (countryNewState, regionNewState) = when (errorMessage) {
                        null -> {
                            val regionsSorted = countries.flattenRegions().sortedBy { region -> region.name }
                            CountryUiState.Content(countries ?: persistentListOf()) to
                                RegionUiState.Content(regions = regionsSorted, filteredRegions = regionsSorted)
                        }

                        else -> CountryUiState.FetchError to RegionUiState.FetchError
                    }
                    _countryState.update { countryNewState }
                    _regionState.update { it.copy(uiState = regionNewState) }
                }
        }
    }

    private fun List<GeoArea.Country>?.flattenRegions(): List<GeoArea.Region> =
        this?.flatMap { country -> country.regions } ?: persistentListOf()
}
