package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Contacts

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<PhonesDto>
)

fun ContactsDto?.toContactsOrNull(): Contacts? = this?.let { contacts ->
    Contacts(
        name = contacts.name,
        email = contacts.email,
        phoneNumbers = contacts.phones.map { it.formatted }
    )
}
