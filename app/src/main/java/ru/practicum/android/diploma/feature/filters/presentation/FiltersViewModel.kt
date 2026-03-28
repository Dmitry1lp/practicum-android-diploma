package ru.practicum.android.diploma.feature.filters.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FiltersViewModel: ViewModel() {

    private val _state = MutableStateFlow(FiltersState())
    val state = _state

    fun onBranchScreen() {
        val check = state.value.isBranchScreen
        _state.update { it.copy(isBranchScreen = !check) }
    }
}
