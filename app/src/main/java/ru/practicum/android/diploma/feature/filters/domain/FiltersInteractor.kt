package ru.practicum.android.diploma.feature.filters.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterIndustry

interface FiltersInteractor {
    fun getIndustries(): Flow<Pair<List<FilterIndustry>?, String?>>

    fun getFiltersSetting(): FiltersSettings?
}
