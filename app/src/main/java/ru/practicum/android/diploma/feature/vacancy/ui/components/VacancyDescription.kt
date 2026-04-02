package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.domain.model.Vacancy

@Composable
fun VacancyDescription(vacancy: Vacancy) {
    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingVeryBig),
        text = stringResource(R.string.vacancy_description),
        style = AppTypography.titleMedium
    )

    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingMedium),
        text = vacancy.description,
        style = AppTypography.bodyLarge
    )
}
