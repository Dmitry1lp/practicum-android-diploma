package ru.practicum.android.diploma.feature.favorite.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.VacancyItem
import ru.practicum.android.diploma.core.presentation.model.toItemData

@Composable
fun FavoritesContentState(
    vacancies: List<Vacancy>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = vacancies,
            key = { vacancy -> vacancy.id }
        ) { vacancy ->
            val vacancyData = vacancy.toItemData()

            VacancyItem(
                data = vacancyData,
                onClick = { onItemClick(vacancyData.id) }
            )
        }
    }
}
