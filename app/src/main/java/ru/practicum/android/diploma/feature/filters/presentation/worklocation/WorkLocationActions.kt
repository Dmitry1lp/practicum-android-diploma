package ru.practicum.android.diploma.feature.filters.presentation.worklocation

data class WorkLocationActions(
    val onBackClick: () -> Unit,
    val onCountryClick: () -> Unit,
    val onRegionClick: () -> Unit,
    val onApplyClick: () -> Unit
)
