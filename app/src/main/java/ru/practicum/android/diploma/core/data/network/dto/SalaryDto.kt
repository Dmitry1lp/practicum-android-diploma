package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.Salary

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

fun SalaryDto?.toSalary(): Salary = Salary(
    lowerBound = this?.from,
    upperBound = this?.to,
    currency = this?.currency
)
