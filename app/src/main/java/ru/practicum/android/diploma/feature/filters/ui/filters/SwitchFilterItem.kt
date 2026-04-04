package ru.practicum.android.diploma.feature.filters.ui.filters

import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.appCheckboxColors
import ru.practicum.android.diploma.core.presentation.components.LabelActionListItem

@Composable
fun SwitchFilterItem(
    text: String,
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    LabelActionListItem(
        modifier = modifier,
        onClick = { onCheckedChange() },
        leadingContent = {
            Text(
                text = text,
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        trailingContent = {
            Checkbox(
                checked = checked,
                onCheckedChange = null,
                colors = appCheckboxColors()
            )
        },
    )
}

@PreviewLightDark
@Composable
private fun SwitchFilterItemPreview() {
    var checked by remember { mutableStateOf(false) }

    DiplomaTheme {
        SwitchFilterItem(
            text = "Не показывать без зарплаты",
            checked = checked,
            onCheckedChange = {
                checked = !checked
            }
        )
    }
}
