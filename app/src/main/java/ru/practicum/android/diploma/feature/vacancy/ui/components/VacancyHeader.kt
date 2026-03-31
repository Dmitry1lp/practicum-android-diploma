package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.domain.model.Vacancy

@Composable
fun VacancyHeader(vacancy: Vacancy) {
    Text(
        text = vacancy.name,
        style = AppTypography.titleLarge
    )
}
