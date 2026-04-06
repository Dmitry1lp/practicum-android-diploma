package ru.practicum.android.diploma.feature.team.domain

data class Developer(
    val name: String,
    val role: String,
    val avatarRes: Int?, // drawable
    val github: String?,
    val telegram: String?
)
