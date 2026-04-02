package ru.practicum.android.diploma.feature.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings

@HiltViewModel
class FiltersViewModel @Inject constructor(
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
        _state.update { it.copy(searchText = text) }
    }

    fun onCheckBox() {
        _state.update { it.copy(isCheckBox = !state.value.isCheckBox) }
    }

    fun onIndustrySelected(industry: FilterIndustry) {
        _state.update { it.copy(industry = industry) }
    }

    fun saveSettings() {
        interactor.saveFiltersSetting(
            FiltersSettings(
                area = state.value.area,
                industry = state.value.industry,
                salaryText = state.value.salaryText,
                isNotShowWithoutSalary = state.value.isCheckBox
            )
        )
    }

    fun clearSettings() {
        interactor.clearSettings()
        _state.value = FiltersUiState()
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
        val filtersSettings = interactor.getFiltersSetting()
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
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
