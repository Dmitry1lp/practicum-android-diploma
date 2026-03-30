package ru.practicum.android.diploma.feature.filters.domain

import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class FiltersSettings(
    val area: FilterArea,
    val industry: FilterIndustry,
    val salaryText: String,
    val isNotShowWithoutSalary: Boolean
)
