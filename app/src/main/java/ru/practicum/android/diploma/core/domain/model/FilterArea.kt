package ru.practicum.android.diploma.core.domain.model

@Deprecated(
    message = "Используйте GeoArea для поддержки иерархии стран и регионов",
    replaceWith = ReplaceWith("GeoArea", "ru.practicum.android.diploma.core.domain.model.GeoArea"),
    level = DeprecationLevel.WARNING
)
data class FilterArea(
    val id: Int,
    val name: String
)
