package ru.practicum.android.diploma.feature.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.domain.interactor.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings

@Serializable
class FiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(FiltersUiState())
    val state = _state

    init {
        getFiltersSettings()
        getIndustries()
    }

    fun onSalaryTextChange(text: String) {
        _state.update { it.copy(salaryText = text) }
    }

    fun onSearchTextChange(text: String) {
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
        val hasActiveFilters =
            state.value.country != null ||
                state.value.region != null ||
                state.value.industry != null ||
                state.value.salaryText.isNotEmpty() ||
                state.value.isCheckBox

        if (hasActiveFilters) {
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
            is Clear.Industry -> _state.update { it.copy(industry = null) }
            is Clear.All -> {
                _state.update {
                    it.copy(
                        country = null,
                        region = null,
                        industry = null,
                        salaryText = "",
                        isCheckBox = false
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
                countries = countries ?: emptyList(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
