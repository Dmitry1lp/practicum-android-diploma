package ru.practicum.android.diploma.feature.filters.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor

class FiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(FiltersUiState())
    val state = _state

    init {
        getIndustries()
    }

    fun onIndustriesScreen() {
        _state.update { it.copy(onIndustriesScreen = !state.value.onIndustriesScreen) }
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

    private fun getIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
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
