package ru.practicum.android.diploma.feature.filters.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.Resource

interface FiltersRepository {

    fun getAreas(): Flow<Resource<List<FilterArea>>>

    fun getIndustries(): Flow<Resource<List<FilterIndustry>>>
}
