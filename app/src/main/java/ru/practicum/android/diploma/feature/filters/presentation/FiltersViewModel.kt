package ru.practicum.android.diploma.feature.filters.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings

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

    fun filterIndustries(searchText: String) {
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
        val hasActiveFilters = state.value.area.name.isNotEmpty() ||
            state.value.industry.name.isNotEmpty() ||
            state.value.salaryText.isNotEmpty() ||
            state.value.isCheckBox

        if (hasActiveFilters) {
            interactor.saveFiltersSetting(
                FiltersSettings(
                    area = state.value.area,
                    industry = state.value.industry,
                    salaryText = state.value.salaryText,
                    isNotShowWithoutSalary = state.value.isCheckBox,
                    isStartSearch = isStartSearch
                )
            )
        }
    }

    fun clear(clear: Clear) {
        when (clear) {
            is Clear.Industry -> _state.update { it.copy(industry = FilterIndustry(0, "")) }
            is Clear.All -> {
                interactor.clearSettings()
                _state.update {
                    Log.d("Nico", "Clear.All")
                    it.copy(
                        area = FilterArea(0, ""),
                        industry = FilterIndustry(0, ""),
                        salaryText = "",
                        isCheckBox = false
                    )
                }
            }
        }
    }

    private fun getIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun getFiltersSettings() {
        val filtersSettings = interactor.getFiltersSettings()
        filtersSettings?.let {
            _state.update {
                it.copy(
                    area = filtersSettings.area,
                    industry = filtersSettings.industry,
                    salaryText = filtersSettings.salaryText,
                    isCheckBox = filtersSettings.isNotShowWithoutSalary
                )
            }
        }
    }

    private fun processResult(foundIndustries: List<FilterIndustry>?, errorMessage: String?) {
        _state.update {
            it.copy(
                industries = foundIndustries?.toImmutableList() ?: immutableListOf(),
                filteredIndustries = foundIndustries?.toImmutableList() ?: immutableListOf(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
