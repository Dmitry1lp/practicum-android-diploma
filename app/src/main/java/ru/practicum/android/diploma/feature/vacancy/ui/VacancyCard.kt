package ru.practicum.android.diploma.feature.vacancy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.appCardColors
import ru.practicum.android.diploma.core.presentation.components.ThumbnailListItem
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyCardData

@Composable
fun VacancyCard(
    data: VacancyCardData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = appCardColors()
    ) {
        ThumbnailListItem(
            modifier = Modifier.padding(16.dp),
            model = data.logoUrl
        ) {
            Column(
                Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = data.industry,
                    style = AppTypography.titleMedium
                )
                Text(
                    text = data.location ?: "",
                    style = AppTypography.bodyLarge
                )
            }
        }
    }
}

private val mockData = VacancyCardData(
    industry = "Еда",
    location = "Москва",
    logoUrl = null
)

@Preview
@Composable
private fun VacancyCardPreview() {
    DiplomaTheme {
        VacancyCard(mockData)
    }
}

@Preview
@Composable
private fun VacancyCardPreviewDark() {
    DiplomaTheme(true) {
        VacancyCard(mockData)
    }
}
