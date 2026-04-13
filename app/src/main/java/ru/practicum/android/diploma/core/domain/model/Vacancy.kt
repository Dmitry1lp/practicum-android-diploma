package ru.practicum.android.diploma.core.domain.model

data class Vacancy(
    val id: String,
    val name: String,
    val description: String,
    val salary: Salary?,
    val address: Address?,
    val areaName: String,
    val experience: String?,
    val schedule: String?,
    val employmentType: String?,
    val contacts: Contacts?,
    val employer: Employer,
    val skills: List<String>,
    val website: String,
    val industry: String
)
