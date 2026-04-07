//@file:Suppress("ForbiddenComment")
//
//package ru.practicum.android.diploma.feature.filters.presentation.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.collections.immutable.toImmutableList
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import okhttp3.internal.immutableListOf
//import ru.practicum.android.diploma.core.domain.model.FilterIndustry
//import ru.practicum.android.diploma.core.domain.model.GeoArea
//import ru.practicum.android.diploma.core.utils.queryFilter
//import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
//import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings
//import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
//import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
//import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
//import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
//import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState
//
//class FiltersViewModelDelete(
//    private val interactor: FiltersInteractor
//) : ViewModel() {
//
//    fun onIndustrySelected(industry: FilterIndustry?) {
//        _industryState.update { it.copy(selectedIndustry = industry) }
//    }
//
//    fun onIndustryApplied(industry: FilterIndustry?) {
//        _filtersUiState.update { it.copy(industry = industry) }
//    }
//
//    fun onSearchTextChange(text: String) = when (val uiState = _industryState.value.uiState) {
//        is IndustryUiState.Content -> _industryState.update { currentState ->
//            currentState.copy(
//                searchText = text,
//                uiState = uiState.copy(
//                    industries = uiState.industries,
//                    filteredIndustries = uiState.industries.queryFilter(text) { it.name }
//                )
//            )
//        }
//
//        else -> _industryState.update { it.copy(searchText = text) }
//    }
//
//    init {
//        getFiltersSettings()
//        getAreas()
//        getIndustries()
//    }
//
//    fun setWorkLocationScreen() {
//        _state.update {
//            it.copy(
//                searchText = "",
//                currentCountry = state.value.country,
//                currentRegion = state.value.region,
//                filteredRegions = state.value.country?.regions ?: state.value.allRegions
//            )
//        }
//    }
//
//    fun setIndustryScreen() {
//        _state.update {
//            it.copy(
//                searchText = "",
//                filteredIndustries = it.industries
//            )
//        }
//    }
//
//    fun updateState(current: Any) {
//        when (current) {
//            is WorkLocationUiState -> _state.update { it.copy(country = current.country, region = current.region) }
//            is GeoArea.Country -> {
//                _state.update { it.copy(currentCountry = current, filteredRegions = current.regions) }
//                if (!current.regions.contains(state.value.currentRegion)) {
//                    _state.update { it.copy(currentRegion = null) }
//                }
//            }
//
//            is GeoArea.Region -> {
//                if (state.value.currentCountry == null) {
//                    val country = state.value.countries.find { it.id == current.countryId }
//                    _state.update {
//                        it.copy(
//                            searchText = "",
//                            currentCountry = country,
//                            currentRegion = current,
//                            filteredRegions = country?.regions ?: immutableListOf()
//                        )
//                    }
//                } else {
//                    _state.update { it.copy(currentRegion = current) }
//                }
//            }
//        }
//    }
//
//    fun onSalaryTextChange(text: String) {
//        _state.update { it.copy(salaryText = text) }
//    }
//
//    fun onSearchRegionTextChange(text: String) {
//        filterRegions(text)
//    }
//
//    private fun filterRegions(searchText: String) {
//        val regions = state.value.currentCountry?.regions ?: state.value.allRegions
//
//        _state.update { currentState ->
//            val filtered = if (searchText.isBlank()) {
//                regions
//            } else {
//                regions.filter { region ->
//                    region.name.lowercase().contains(searchText.lowercase())
//                }
//            }
//            currentState.copy(
//                searchText = searchText,
//                filteredRegions = filtered
//            )
//        }
//    }
//
//    fun onSearchIndustryTextChange(text: String) {
//        filterIndustries(text)
//    }
//
//    private fun filterIndustries(searchText: String) {
//        _state.update { currentState ->
//            val filtered = if (searchText.isBlank()) {
//                currentState.industries
//            } else {
//                currentState.industries.filter { industry ->
//                    industry.name.lowercase().contains(searchText.lowercase())
//                }
//            }
//            currentState.copy(
//                searchText = searchText,
//                filteredIndustries = filtered
//            )
//        }
//    }
//
//    fun onCheckBox() {
//        _filtersUiState.update { it.copy(isCheckBox = !filtersUiState.value.isCheckBox) }
//    }
//
//    fun saveSettings(isStartSearch: Boolean) {
//        if (state.value.isFiltersSettings) {
//        val selectedIndustry = industryState.value.selectedIndustry
//
//        val hasActiveFilters =
//            filtersUiState.value.country != null ||
//                filtersUiState.value.region != null ||
//                selectedIndustry != null ||
//                filtersUiState.value.salaryText.isNotEmpty() ||
//                filtersUiState.value.isCheckBox
//
//        if (hasActiveFilters) {
//            interactor.saveFiltersSetting(
//                FiltersSettings(
//                    country = filtersUiState.value.country,
//                    region = filtersUiState.value.region,
//                    industry = selectedIndustry,
//                    salaryText = filtersUiState.value.salaryText.ifEmpty { null },
//                    onlyWithSalary = filtersUiState.value.isCheckBox.let { if (!it) null else true },
//                    isStartSearch = isStartSearch
//                )
//            )
//        } else {
//            clear(ClearTarget.AppPreferences)
//        }
//    }
//}
