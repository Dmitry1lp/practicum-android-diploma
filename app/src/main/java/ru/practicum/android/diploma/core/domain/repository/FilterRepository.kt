package ru.practicum.android.diploma.core.domain.repository

import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.Industry

interface FilterRepository {

    suspend fun getAreas(): List<FilterArea>

    suspend fun getIndustries(): List<Industry>
}
