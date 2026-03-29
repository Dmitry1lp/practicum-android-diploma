package ru.practicum.android.diploma.feature.filters.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Industry
import ru.practicum.android.diploma.core.domain.model.Resource

interface FiltersRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
}
