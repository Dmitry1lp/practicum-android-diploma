package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.VacancyItem
import ru.practicum.android.diploma.core.presentation.model.VacancyItemData
import ru.practicum.android.diploma.core.utils.toLocalizedString

@Composable
fun VacancyList(
    vacancies: List<Vacancy>,
    modifier: Modifier = Modifier,
    onVacancyClick: (Vacancy) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 38.dp)
    ) {
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
private fun Vacancy.toItemData(): VacancyItemData {
    return VacancyItemData(
        id = id,
        name = name,
        location = address?.city.orEmpty(),
        imageUrl = employer.logoUrl.orEmpty(),
        industry = industry,
        salary = salary.toLocalizedString()
    )
}
