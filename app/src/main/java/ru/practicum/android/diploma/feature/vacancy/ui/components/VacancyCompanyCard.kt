package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.presentation.toCardData
import ru.practicum.android.diploma.feature.vacancy.ui.VacancyCard

@Composable
fun VacancyCompanyCard(vacancy: Vacancy) {
    VacancyCard(
        modifier = Modifier.padding(top = AppDimensions.paddingMedium),
        data = vacancy.toCardData()
    )
}
