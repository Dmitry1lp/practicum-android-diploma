package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy

data class VacancyCardDto(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val salary: SalaryDto?,
    val logo: String?
)

fun VacancyCardDto.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    description = "",
    salary = Salary(
        lowerBound = salary?.from,
        upperBound = salary?.to,
        currency = salary?.currency
    ),
    address = city?.let {
        Address(
            city = it,
            street = "",
            building = ""
        )
    },
    areaName = city ?: "",
    experience = null,
    schedule = null,
    employmentType = null,
    contacts = null,
    employer = Employer(
        name = company ?: "",
        // logoUrl = logo?.fixImageSize() ?: ""
        logoUrl = logo ?: ""
    ),
    skills = emptyList(),
    website = "",
    industry = company ?: ""
)
