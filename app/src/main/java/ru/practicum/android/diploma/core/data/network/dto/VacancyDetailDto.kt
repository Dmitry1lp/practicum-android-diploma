package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Vacancy

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val description: String?,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto?,
    val area: FilterAreaDto?,
    val skills: List<String>?,
    val url: String?,
    val industry: IndustryDto?
) : Response()

fun VacancyDetailDto.toVacancy(): Vacancy = Vacancy(
    id = id,
    name = name,
    description = description ?: "",
    salary = salary.toSalary(),
    address = address.toAddressOrNull(),
    areaName = area?.name ?: "",
    experience = experience?.name,
    schedule = schedule?.name,
    employmentType = employment?.name,
    contacts = contacts?.toContactsOrNull(),
    employer = employer.toEmployer(),
    skills = skills ?: emptyList(),
    website = url ?: "",
    industry = industry?.name ?: ""
)
