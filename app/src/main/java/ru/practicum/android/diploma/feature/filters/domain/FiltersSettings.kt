package ru.practicum.android.diploma.feature.filters.domain

import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FiltersSettings(
    val area: GeoArea? = null,
    val industry: FilterIndustry? = null,
    val salaryText: String? = null,
    val onlyWithSalary: Boolean? = null,
    val isStartSearch: Boolean = false
)
