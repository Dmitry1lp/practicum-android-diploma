package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@Composable
fun SelectableFilterItem(
    text: String?,
    hint: String,
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    text?.let {
        HintedFilterItem(
            modifier = modifier,
            text = text,
            hint = hint,
            onClick = onClick,
            onIconClick = onIconClick
        )
    } ?: run {
        FilterItem(
            modifier = modifier,
            text = hint,
            isActive = false,
            onClick = onClick
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun SelectableFilterItemPreview_Unselected() {
    DiplomaTheme {
        SelectableFilterItem(
            text = null,
            hint = "Страна",
            onClick = {},
            onIconClick = {}
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun SelectableFilterItemPreview_Selected() {
    DiplomaTheme {
        SelectableFilterItem(
            text = "Россия",
            hint = "Страна",
            onClick = {},
            onIconClick = {}
        )
    }
}
