package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.VacancyItem
import ru.practicum.android.diploma.core.presentation.model.VacancyItemData

@Composable
fun VacancyList(
    vacancies: List<Vacancy>,
    onVacancyClick: (Vacancy) -> Unit
) {
    LazyColumn {
        items(
            items = vacancies,
            key = { it.id }
        ) { vacancy ->
            VacancyItem(
                data = vacancy.toItemData(),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onVacancyClick(vacancy) }
            )
        }
    }
}

@Composable
private fun Vacancy.formatSalary(): String {
    return when {
        this.salary?.lowerBound != null && this.salary.upperBound != null ->
            "от ${this.salary.lowerBound} до ${this.salary.upperBound} ${this.salary.currency.orEmpty()}"

        this.salary?.lowerBound != null ->
            "от ${this.salary.lowerBound} ${this.salary.currency.orEmpty()}"

        this.salary?.upperBound != null ->
            "до ${this.salary.upperBound} ${this.salary.currency.orEmpty()}"

        else -> stringResource(R.string.vacancy_salary_not_specified)
    }
}

@Composable
private fun Vacancy.toItemData(): VacancyItemData {
    return VacancyItemData(
        id = this.id,
        name = this.name,
        location = this.address?.city.orEmpty(),
        imageUrl = this.employer.logoUrl.orEmpty(),
        industry = this.industry,
        salary = this.formatSalary()
    )
}
