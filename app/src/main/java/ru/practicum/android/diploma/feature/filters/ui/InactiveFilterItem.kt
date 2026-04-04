package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.LabelActionListItem

@Deprecated(
    message = "Используйте FilterItem с параметром isActive = false",
    replaceWith = ReplaceWith(
        "FilterItem(text = text, onClick = onClick, isActive = false, modifier = modifier)",
    ),
    level = DeprecationLevel.WARNING
)
@Composable
fun InactiveFilterItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LabelActionListItem(
        modifier = modifier,
        onClick = onClick,
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

@PreviewLightDark
@Composable
private fun InactiveFilterItemPreview() {
    DiplomaTheme {
        InactiveFilterItem(
            text = "Отрасль",
            onClick = {}
        )
    }
}
