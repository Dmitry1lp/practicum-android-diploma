package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.LabelActionListItem

@Composable
fun FilterItem(
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val color = if (isActive) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary

    LabelActionListItem(
        modifier = modifier,
        onClick = onClick,
        leadingContent = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = color
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
@PreviewLightDark
@Composable
private fun ActiveFilterItemPreview() {
    DiplomaTheme {
        FilterItem(
            text = "Отрасль",
            isActive = true,
            onClick = {}
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun InactiveFilterItemPreview() {
    DiplomaTheme {
        FilterItem(
            text = "Отрасль",
            isActive = false,
            onClick = {}
        )
    }
}
