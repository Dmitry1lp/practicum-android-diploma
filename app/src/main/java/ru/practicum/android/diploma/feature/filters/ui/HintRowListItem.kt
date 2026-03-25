package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.RowListItem
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

@Composable
fun HintRowListItem(
    text: String,
    hint: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    RowListItem(
        modifier = modifier.antiRepetitionClick { onClick() },
        leadingContent = {
            Column {
                Text(
                    text = hint,
                    style = AppTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = text,
                    style = AppTypography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_close_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}

@Preview
@Composable
fun HintRowListItemPreview() {
    DiplomaTheme {
        HintRowListItem(
            text = "Россия, Москва",
            hint = "Место работы",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun HintRowListItemPreviewDark() {
    DiplomaTheme(true) {
        HintRowListItem(
            text = "Россия, Москва",
            hint = "Место работы",
            onClick = {}
        )
    }
}
