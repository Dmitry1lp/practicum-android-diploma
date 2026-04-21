package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.feature.vacancy.ui.model.DescriptionBlock
import ru.practicum.android.diploma.feature.vacancy.utils.parseHtml

@Composable
fun VacancyDescriptionHtml(html: String) {
    val blocks = remember(html) { parseHtml(html) }

    Column {
        blocks.forEach { block ->
            when (block) {

                is DescriptionBlock.Header -> {
                    Text(
                        text = block.text,
                        style = AppTypography.titleMedium,
                        modifier = Modifier.padding(top = AppDimensions.paddingMedium)
                    )
                }

                is DescriptionBlock.Paragraph -> {
                    Text(
                        text = block.text,
                        style = AppTypography.bodyLarge,
                        modifier = Modifier.padding(top = AppDimensions.paddingSmall)
                    )
                }

                is DescriptionBlock.ListBlock -> {
                    Column(
                        modifier = Modifier.padding(
                            top = AppDimensions.paddingSmall
                        )
                    ) {
                        block.items.forEach {
                            Text(
                                text = "• $it",
                                style = AppTypography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
