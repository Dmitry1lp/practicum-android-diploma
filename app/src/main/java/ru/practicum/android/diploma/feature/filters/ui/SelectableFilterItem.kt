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
    modifier: Modifier = Modifier,
    onSelectedClick: () -> Unit = {},
    onUnselectedClick: () -> Unit = {}
) {
    text?.let {
        HintedFilterItem(
            modifier = modifier,
            text = text,
            hint = hint,
            onClick = onSelectedClick
        )
    } ?: run {
        FilterItem(
            modifier = modifier,
            text = hint,
            isActive = false,
            onClick = onUnselectedClick
        )
    }
}

@Preview
@PreviewLightDark
@Composable
fun SelectableFilterItemPreview_Unselected() {
    DiplomaTheme {
        SelectableFilterItem(
            text = null,
            hint = "Страна"
        )
    }
}

@Preview
@PreviewLightDark
@Composable
fun SelectableFilterItemPreview_Selected() {
    DiplomaTheme {
        SelectableFilterItem(
            text = "Россия",
            hint = "Страна"
        )
    }
}
