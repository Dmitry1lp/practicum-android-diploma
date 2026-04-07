package ru.practicum.android.diploma.feature.filters.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.domain.model.Resource
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings

interface FiltersRepository {

    fun getCountries(): Flow<Resource<List<GeoArea.Country>>>

    fun getIndustries(): Flow<Resource<List<FilterIndustry>>>

    fun getFiltersSettings(): FiltersSettings?

    fun saveFiltersSetting(settings: FiltersSettings)

    fun clearSettings()
}
