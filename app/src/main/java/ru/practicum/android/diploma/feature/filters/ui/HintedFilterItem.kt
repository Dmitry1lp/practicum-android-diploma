package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.LabelActionListItem

@Composable
fun HintedFilterItem(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onClick: () -> Unit,
    onIconClick: () -> Unit = {}
) {
    LabelActionListItem(
        modifier = modifier,
        onClick = onClick,
        leadingContent = {
            Column {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        trailingContent = {
            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24),
                    contentDescription = stringResource(R.string.cd_reset),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun HintedFilterItemPreview() {
    DiplomaTheme {
        HintedFilterItem(
            text = "Россия, Москва",
            hint = "Место работы",
            onClick = {}
        )
    }
}
