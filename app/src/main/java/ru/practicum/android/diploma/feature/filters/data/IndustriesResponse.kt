package ru.practicum.android.diploma.feature.filters.data

import ru.practicum.android.diploma.core.data.network.dto.IndustryDto
import ru.practicum.android.diploma.core.data.network.dto.Response

data class IndustriesResponse(val results: List<IndustryDto>) : Response()
