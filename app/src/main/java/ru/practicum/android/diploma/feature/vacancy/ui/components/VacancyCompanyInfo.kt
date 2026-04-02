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
fun VacancyCompanyInfo(vacancy: Vacancy) {
    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingMedium),
        text = stringResource(R.string.vacancy_company_title),
        style = AppTypography.titleMedium
    )

    Text(
        text = vacancy.employer.name,
        style = AppTypography.bodyLarge
    )
}
