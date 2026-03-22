package ru.practicum.android.diploma.core.domain.model

data class Vacancy(
    val id: String,
    val name: String,
    val description: String,
    val employerName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val city: String,
    val skills: List<String>,
    val url: String
)
