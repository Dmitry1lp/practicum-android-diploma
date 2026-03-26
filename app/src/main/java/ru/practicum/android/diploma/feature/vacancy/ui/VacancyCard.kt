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

@Composable
fun VacancyCard(
    modifier: Modifier = Modifier,
    model: Any? = null
) {
    Card(
        modifier = modifier,
        colors = appCardColors()
    ) {
        ThumbnailListItem(
            modifier = Modifier.padding(16.dp),
            model = model
        ) {
            Column(
                Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "Text",
                    style = AppTypography.titleMedium
                )
                Text(
                    text = "Text",
                    style = AppTypography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun VacancyCardPreview() {
    DiplomaTheme {
        VacancyCard()
    }
}

@Preview
@Composable
private fun VacancyCardPreviewDark() {
    DiplomaTheme(true) {
        VacancyCard()
    }
}
