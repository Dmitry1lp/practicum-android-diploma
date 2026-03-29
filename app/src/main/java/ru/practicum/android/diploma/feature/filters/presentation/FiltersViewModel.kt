package ru.practicum.android.diploma.feature.filters.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import ru.practicum.android.diploma.core.domain.model.Industry
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor

class FiltersViewModel(
    private val interactor: FiltersInteractor
): ViewModel() {

    private val _state = MutableStateFlow(FiltersState())
    val state = _state

    init {
        getIndustries()
    }

    fun onSwitchClicked() {
        _state.update { it.copy(isWithoutSalary = !state.value.isWithoutSalary) }
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

    private fun processResult(foundIndustries: List<Industry>?, errorMessage: String?) {
        Log.d("Nico", "Список индустрий = $foundIndustries\nСообщение об ошибке = $errorMessage")
        _state.update {
            it.copy(
                industries = foundIndustries?.toImmutableList() ?: immutableListOf(),
                errorMessage = errorMessage ?: ""
            )
        }
    }
}
