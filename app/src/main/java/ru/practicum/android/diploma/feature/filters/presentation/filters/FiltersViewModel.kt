package ru.practicum.android.diploma.feature.filters.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.domain.interactor.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState

@Serializable
class FiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

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
        val regions = state.value.currentCountry?.regions ?: state.value.allRegions

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
            clear(Clear.Settings)
        }
    }

    fun clear(clear: Clear) {
        when (clear) {
            is Clear.WorkLocation -> _state.update {
                it.copy(
                    country = null,
                    currentCountry = null,
                    region = null,
                    currentRegion = null,
                    filteredRegions = state.value.allRegions
                )
            }

            is Clear.Country -> _state.update {
                it.copy(
                    currentCountry = null,
                    currentRegion = null,
                    filteredRegions = state.value.allRegions
                )
            }

            is Clear.Region -> _state.update { it.copy(currentRegion = null) }
            is Clear.Industry -> _state.update { it.copy(industry = null) }
            is Clear.All -> {
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

            is Clear.Settings -> interactor.clearSettings()
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
        _state.update {
            it.copy(
                countries = countries?.toImmutableList() ?: immutableListOf(),
                allRegions = countries?.flatMap { country -> country.regions } ?: immutableListOf(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
