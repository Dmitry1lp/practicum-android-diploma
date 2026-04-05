package ru.practicum.android.diploma.feature.filters.ui.filters

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.appRadioButtonColors
import ru.practicum.android.diploma.core.presentation.components.LabelActionListItem

@Composable
fun RadioButtonItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    LabelActionListItem(
        modifier = modifier,
        onClick = onClick,
        leadingContent = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = appRadioButtonColors()
            )
        }
    )
}
