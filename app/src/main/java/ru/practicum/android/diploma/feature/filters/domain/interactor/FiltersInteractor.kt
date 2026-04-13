package ru.practicum.android.diploma.feature.filters.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings

interface FiltersInteractor {
    fun getCountries(): Flow<Pair<List<GeoArea.Country>?, String?>>

    fun getIndustries(): Flow<Pair<List<FilterIndustry>?, String?>>

    fun getFiltersSettings(): FiltersSettings?

    fun saveFiltersSettings(settings: FiltersSettings)

    fun clearSettings()
}
