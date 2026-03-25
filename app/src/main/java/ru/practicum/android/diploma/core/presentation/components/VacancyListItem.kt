package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@Composable
fun VacancyListItem(
    title: String,
    industry: String,
    salary: String,
    modifier: Modifier = Modifier,
    model: Any? = null
) {
    ImageListItem(
        modifier = modifier,
        model = model
    ) {
        Column {
            Text(
                text = title,
                style = AppTypography.titleMedium
            )
            Text(
                text = industry,
                style = AppTypography.bodyLarge
            )
            Text(
                text = salary,
                style = AppTypography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun VacancyListItemPreviewLight() {
    DiplomaTheme(false) {
        VacancyListItem(
            model = null,
            title = "Android-разработчик, Москва",
            industry = "Еда",
            salary = "от 100 000 ₽"
        )
    }
}

@Preview
@Composable
private fun VacancyListItemPreviewDark() {
    DiplomaTheme(true) {
        VacancyListItem(
            model = null,
            title = "Android-разработчик, Москва",
            industry = "Еда",
            salary = "от 100 000 ₽"
        )
    }
}
