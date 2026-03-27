package ru.practicum.android.diploma.core.data.network.dto

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<PhonesDto>
)

