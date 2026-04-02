package ru.practicum.android.diploma.feature.filters.presentation

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class FiltersUiState(
    val onIndustriesScreen: Boolean = false,
    val area: FilterArea = FilterArea(0, ""),
    val industry: FilterIndustry = FilterIndustry(0, ""),
    val salaryText: String = "",
    val searchText: String = "",
    val isCheckBox: Boolean = false,
    val industries: List<FilterIndustry> = immutableListOf(),
    val errorMessage: String = ""
)

data class FiltersActions(
    val onBackClick: () -> Unit = {},
    val onIndustriesScreen: () -> Unit = {},
    val onSalaryTextChange: (String) -> Unit = {},
    val onCheckBox: () -> Unit = {},
    val onSearchTextChange: (String) -> Unit = {}
)
