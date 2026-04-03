package ru.practicum.android.diploma.feature.filters.domain

import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

data class FiltersSettings(
    val area: FilterArea = FilterArea(0, ""),
    val industry: FilterIndustry = FilterIndustry(0, ""),
    val salaryText: String = "",
    val isNotShowWithoutSalary: Boolean = false,
    val isStartSearch: Boolean = false
)
