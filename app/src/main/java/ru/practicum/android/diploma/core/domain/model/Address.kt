package ru.practicum.android.diploma.core.domain.model

class Address(
    val city: String,
    val street: String,
    val building: String
) {
    val fullAddress: String = "$city, $street, $building"
}
