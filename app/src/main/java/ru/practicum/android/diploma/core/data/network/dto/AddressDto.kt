package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Address

data class AddressDto(
    val city: String,
    val street: String,
    val building: String,
    val raw: String
)

fun AddressDto?.toAddressOrNull(): Address? = this?.let { address ->
    Address(
        city = address.city,
        street = address.street,
        building = address.building
    )
}
