package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.model.VacancyItemData
import ru.practicum.android.diploma.core.presentation.model.toItemData
import ru.practicum.android.diploma.core.presentation.preview.VacancyPreviewData
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

@Composable
fun VacancyItem(
    data: VacancyItemData,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val textItems = listOf(
        "${data.name}, ${data.location}" to MaterialTheme.typography.titleMedium,
        data.industry to MaterialTheme.typography.bodyLarge,
        data.salary to MaterialTheme.typography.bodyLarge
    )

    ThumbnailListItem(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .antiRepetitionClick { onClick(data.id) }
            .padding(AppDimensions.VacancyItem.contentPadding),
        model = data.imageUrl,
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
@PreviewLightDark
@Composable
private fun VacancyItemPreview() {
    DiplomaTheme {
        VacancyItem(
            data = VacancyPreviewData.vacancy.toItemData(),
            onClick = {}
        )
    }
}
