package ru.practicum.android.diploma.feature.filters.presentation.filters

import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget

data class FiltersActions(
    val onBackClick: () -> Unit,
    val onWorkLocationClick: () -> Unit,
    val onIndustryClick: () -> Unit,
    val onSalaryTextChange: (String) -> Unit,
    val onCheckBoxChange: () -> Unit,
    val onApplyClick: (Any?) -> Unit,
    val onClearClick: (ClearTarget) -> Unit
)
