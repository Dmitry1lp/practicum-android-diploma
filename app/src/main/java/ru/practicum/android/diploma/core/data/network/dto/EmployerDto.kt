package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Employer

data class EmployerDto(
    val id: String,
    val name: String,
    val logo: String?
)

fun EmployerDto?.toEmployer(): Employer = Employer(
    name = this?.name ?: "",
    logoUrl = this?.logo ?: ""
)
