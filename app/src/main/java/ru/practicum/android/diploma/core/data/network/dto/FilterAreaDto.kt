package ru.practicum.android.diploma.core.data.network.dto
data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
)

