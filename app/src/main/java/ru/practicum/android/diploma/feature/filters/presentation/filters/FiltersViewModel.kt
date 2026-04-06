@file:Suppress("ForbiddenComment")

package ru.practicum.android.diploma.feature.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState

class FiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

    private val _workLocationState = MutableStateFlow(WorkLocationUiState()) // TODO: забирать актуальное состояние
    val workLocationState: StateFlow<WorkLocationUiState> = _workLocationState.asStateFlow()

    private val _state = MutableStateFlow(FiltersUiState())
    val state = _state

    init {
        getFiltersSettings()
        getAreas()
        getIndustries()
    }

    fun setWorkLocationScreen() {
        _state.update {
            it.copy(
                searchText = "",
                currentCountry = state.value.country,
                currentRegion = state.value.region,
                filteredRegions = state.value.country?.regions ?: state.value.allRegions
            )
        }
    }

    fun setIndustryScreen() {
        _state.update {
            it.copy(
                searchText = "",
                filteredIndustries = it.industries
            )
        }
    }

    fun updateState(current: Any) {
        when (current) {
            is WorkLocationUiState -> _state.update { it.copy(country = current.country, region = current.region) }
            is GeoArea.Country -> {
                _state.update { it.copy(currentCountry = current, filteredRegions = current.regions) }
                if (!current.regions.contains(state.value.currentRegion)) {
                    _state.update { it.copy(currentRegion = null) }
                }
            }

            is GeoArea.Region -> {
                if (state.value.currentCountry == null) {
                    val country = state.value.countries.find { it.id == current.countryId }
                    _state.update {
                        it.copy(
                            searchText = "",
                            currentCountry = country,
                            currentRegion = current,
                            filteredRegions = country?.regions ?: immutableListOf()
                        )
                    }
                } else {
                    _state.update { it.copy(currentRegion = current) }
                }
            }
        }
    }

    fun onSalaryTextChange(text: String) {
        _state.update { it.copy(salaryText = text) }
    }

    fun onSearchRegionTextChange(text: String) {
        filterRegions(text)
    }

    private fun filterRegions(searchText: String) {
        val regions = state.value.currentCountry?.regions?.sortedBy { it.name } ?: state.value.allRegions

        _state.update { currentState ->
            val filtered = if (searchText.isBlank()) {
                regions
            } else {
                regions.filter { region ->
                    region.name.lowercase().contains(searchText.lowercase())
                }
            }
            currentState.copy(
                searchText = searchText,
                filteredRegions = filtered
            )
        }
    }

    fun onSearchIndustryTextChange(text: String) {
        filterIndustries(text)
    }

    private fun filterIndustries(searchText: String) {
        _state.update { currentState ->
            val filtered = if (searchText.isBlank()) {
                currentState.industries
            } else {
                currentState.industries.filter { industry ->
                    industry.name.lowercase().contains(searchText.lowercase())
                }
            }
            currentState.copy(
                searchText = searchText,
                filteredIndustries = filtered
            )
        }
    }

    fun onCheckBox() {
        _state.update { it.copy(isCheckBox = !state.value.isCheckBox) }
    }

    fun onIndustrySelected(industry: FilterIndustry) {
        _state.update { it.copy(industry = industry) }
    }

    fun saveSettings(isStartSearch: Boolean) {
        if (state.value.isFiltersSettings) {
            interactor.saveFiltersSetting(
                FiltersSettings(
                    country = state.value.country,
                    region = state.value.region,
                    industry = state.value.industry,
                    salaryText = state.value.salaryText.ifEmpty { null },
                    onlyWithSalary = state.value.isCheckBox.let { if (!it) null else true },
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
                _state.update {
                    it.copy(
                        country = null,
                        region = null,
                        industry = null,
                        salaryText = "",
                        isCheckBox = false,
                        currentCountry = null,
                        currentRegion = null
                    )
                }
            }

            is ClearTarget.Industry -> _state.update { it.copy(industry = null) }
            is ClearTarget.Country -> _state.update {
                it.copy(
                    currentCountry = null,
                    currentRegion = null,
                    filteredRegions = state.value.allRegions
                )
            }
            is ClearTarget.Region -> _state.update { it.copy(currentRegion = null) }
            is ClearTarget.WorkLocation -> _state.update {
                it.copy(
                    country = null,
                    currentCountry = null,
                    region = null,
                    currentRegion = null,
                    filteredRegions = state.value.allRegions
                )
            }
        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = interactor.getFiltersSettings()
        filtersSettings?.let {
            _state.update {
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

    private fun getAreas() {
        viewModelScope.launch {
            interactor
                .getCountries()
                .collect { pair ->
                    progressAreasResult(pair.first, pair.second)
                }
        }
    }

    private fun progressAreasResult(
        countries: List<GeoArea.Country>?,
        errorMessage: String?
    ) {
        _state.update {
            it.copy(
                countries = countries ?: immutableListOf(),
                allRegions = countries?.flatMap { country -> country.regions }?.sortedBy { region -> region.name }
                    ?: immutableListOf(),
                errorMessage = errorMessage ?: ""
            )
        }
    }

    private fun getIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { pair ->
                    processIndustriesResult(pair.first, pair.second)
                }
        }
    }

    private fun processIndustriesResult(foundIndustries: List<FilterIndustry>?, errorMessage: String?) {
        _state.update {
            it.copy(
                industries = foundIndustries?.toImmutableList() ?: immutableListOf(),
                filteredIndustries = foundIndustries?.toImmutableList() ?: immutableListOf(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
