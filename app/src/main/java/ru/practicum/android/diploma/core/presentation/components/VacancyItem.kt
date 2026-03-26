package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.internal.immutableListOf
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@Composable
fun VacancyItem(
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

    ThumbnailListItem(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(AppDimensions.VacancyItem.contentPadding),
        model = model,
        imageBorder = BorderStroke(
            width = AppDimensions.VacancyItem.imageBorderWidth,
            color = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            Modifier.padding(start = AppDimensions.VacancyItem.contentGap)
        ) {
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
private fun VacancyItemPreview() {
    DiplomaTheme {
        VacancyItem(
            model = null,
            title = "Android-разработчик, Москва",
            industry = "Еда",
            salary = "от 100 000 ₽"
        )
    }
}

@Preview
@Composable
private fun VacancyItemPreviewDark() {
    DiplomaTheme(true) {
        VacancyItem(
            model = null,
            title = "Android-разработчик, Москва",
            industry = "Еда",
            salary = "от 100 000 ₽"
        )
    }
}
