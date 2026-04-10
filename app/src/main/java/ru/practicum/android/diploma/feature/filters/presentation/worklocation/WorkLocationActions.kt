package ru.practicum.android.diploma.feature.filters.presentation.worklocation

import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget

data class WorkLocationActions(
    val onBackClick: () -> Unit,
    val onCountryClick: () -> Unit,
    val onRegionClick: () -> Unit,
    val onClearClick: (ClearTarget) -> Unit,
    val onApplyClick: (WorkLocation) -> Unit
)
