package ru.practicum.android.diploma.feature.filters.presentation

import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.core.domain.model.Industry

data class FiltersState(
    val area: String = "",
    val industry: String = "",
    val salary: String = "",
    val isWithoutSalary: Boolean = false,
    val industries: List<Industry> = immutableListOf(),
    val errorMessage: String = ""
)
