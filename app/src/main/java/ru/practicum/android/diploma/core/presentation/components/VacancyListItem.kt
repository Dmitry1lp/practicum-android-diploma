package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.internal.immutableListOf
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
    val textItems = remember {
        immutableListOf(
            title to AppTypography.titleMedium,
            industry to AppTypography.bodyLarge,
            salary to AppTypography.bodyLarge
        )
    }

    ImageListItem(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        model = model
    ) {
        Column {
            textItems.forEach { (text, style) ->
                Text(
                    text = text,
                    style = style,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
private fun VacancyListItemPreviewLight() {
    DiplomaTheme() {
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
