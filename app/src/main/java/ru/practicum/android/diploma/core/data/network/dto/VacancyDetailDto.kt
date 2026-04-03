package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.utils.fixImageSize

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val area: FilterAreaDto,
    val skills: List<String>,
    val url: String,
    val industry: IndustryDto
) : Response()

fun VacancyDetailDto.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    description = description,
    salary = Salary(
        lowerBound = salary?.from,
        upperBound = salary?.to,
        currency = salary?.currency
    ),
    address = address?.let {
        Address(
            city = address.city,
            street = address.street,
            building = address.building
        )
    },
    experience = experience?.name,
    schedule = schedule?.name,
    employmentType = employment?.name,
    contacts = contacts?.let {
        Contacts(
            name = contacts.name,
            email = contacts.email,
            phoneNumbers = contacts.phones.map { it.formatted }
        )
    },
    employer = Employer(
        name = employer.name,
        logoUrl = employer.logo.fixImageSize() ?: ""
    ),
    skills = skills,
    website = url,
    industry = industry.name
)
