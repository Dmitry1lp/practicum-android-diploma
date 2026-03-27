package ru.practicum.android.diploma.core.domain.model

data class Contacts(
    val name: String,
    val email: String,
    val phoneNumbers: List<String>
)
