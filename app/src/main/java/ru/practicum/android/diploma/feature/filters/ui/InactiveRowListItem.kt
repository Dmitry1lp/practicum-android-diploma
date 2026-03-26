package ru.practicum.android.diploma.feature.filters.ui

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
fun InactiveRowListItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    RowListItem(
        modifier = modifier.antiRepetitionClick { onClick() },
        leadingContent = {
            Text(
                text = text,
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}

@Preview
@Composable
private fun InactiveRowListItemPreview() {
    DiplomaTheme {
        InactiveRowListItem(
            text = "Отрасль",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun InactiveRowListItemPreviewDark() {
    DiplomaTheme(true) {
        InactiveRowListItem(
            text = "Отрасль",
            onClick = {}
        )
    }
}
