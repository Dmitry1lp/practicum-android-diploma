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
fun VacancyConditions(vacancy: Vacancy) {
    val experience = vacancy.experience?.takeIf { it.isNotBlank() }
    val employmentType = vacancy.employmentType?.takeIf { it.isNotBlank() }
    val schedule = vacancy.schedule?.takeIf { it.isNotBlank() }

    if (experience == null && employmentType == null && schedule == null) return

    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingMedium),
        text = stringResource(R.string.vacancy_required_experience),
        style = AppTypography.titleSmall
    )

    experience?.let {
        Text(text = it, style = AppTypography.bodyMedium)
    }

    val rowText = listOfNotNull(employmentType, schedule)
        .joinToString(". ")

    if (rowText.isNotBlank()) {
        Text(text = rowText, style = AppTypography.bodyMedium)
    }
}
