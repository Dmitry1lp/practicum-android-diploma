package ru.practicum.android.diploma.core.data.network.dto

data class IndustriesResponse(
    val industries: List<FilterIndustryDto>
) : Response()
