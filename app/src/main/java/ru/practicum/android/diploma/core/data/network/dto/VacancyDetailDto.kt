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
    val industry: FilterIndustryDto?
) : Response() {
    fun VacancyDetailDto.toDomain(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            description = description ?: "",
            employerName = employer?.name ?: "",
            salaryFrom = salary?.from,
            salaryTo = salary?.to,
            currency = salary?.currency,
            city = address?.city ?: area?.name ?: "",
            skills = skills ?: emptyList(),
            url = url ?: ""
        )
    }
}
