package ru.practicum.android.diploma.core.data.network.dto

sealed interface Request {
    object AreasRequest : Request
    object IndustriesRequest : Request

    data class VacancyDetailsRequest(
        val id: String
    ) : Request

    data class VacancySearchRequest(
        val params: Map<String, String>
    ) : Request
}
