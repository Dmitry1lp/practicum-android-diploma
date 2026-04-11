package ru.practicum.android.diploma.feature.filters.data.model

import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea

data class FiltersSettings(
    val country: GeoArea.Country? = null,
    val region: GeoArea.Region? = null,
    val industry: FilterIndustry? = null,
    val salaryText: String? = null,
    val onlyWithSalary: Boolean? = null,
    val isStartSearch: Boolean = false
) {
    val areaId: Int?
        get() = region?.id ?: country?.id

    val salary: Int?
        get() = salaryText?.toIntOrNull()
}
