package ru.practicum.android.diploma.core.utils

import android.icu.util.Currency
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Salary

@Composable
fun Salary?.toLocalizedString(): String {
    if (this == null) return stringResource(R.string.vacancy_salary_not_specified)

    val currency = currency?.currencySign ?: ""

    return when {
        lowerBound == null && upperBound == null -> stringResource(
            R.string.vacancy_salary_not_specified
        )

        lowerBound == null -> stringResource(
            R.string.vacancy_salary_to,
            upperBound!!.formatWithSpaces(),
            currency
        )

        upperBound == null -> stringResource(
            R.string.vacancy_salary_from,
            lowerBound.formatWithSpaces(),
            currency
        )

        else -> stringResource(
            R.string.vacancy_salary_range,
            lowerBound.formatWithSpaces(),
            upperBound.formatWithSpaces(),
            currency
        )
    }
}

private val String.currencySign: String
    get() = Currency.getInstance(this).symbol
