package ru.practicum.android.diploma.feature.filters.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Industry

interface FiltersInteractor {
    fun getIndustries(): Flow<Pair<List<Industry>?, String?>>
}
