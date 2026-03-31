package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography

@Composable
fun VacancySkills(skills: List<String>) {
    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingBig),
        text = stringResource(R.string.vacancy_key_skills),
        style = AppTypography.titleMedium
    )

    Column(modifier = Modifier.padding(top = AppDimensions.paddingMedium)) {
        skills.forEach { skill ->
            Row {
                Text("•", modifier = Modifier.padding(end = AppDimensions.paddingVerySmall))
                Text(skill)
            }
        }
    }
}
