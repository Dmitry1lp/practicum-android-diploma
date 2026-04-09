package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

private const val ANIMATION_DURATION_MILLIS = 400

@Composable
fun SelectableFilterItem(
    text: String?,
    hint: String,
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedModifier = modifier.animateContentSize(
        animationSpec = tween(ANIMATION_DURATION_MILLIS)
    )

    Box(animatedModifier) {
        text?.let {
            HintedFilterItem(
                text = text,
                hint = hint,
                onClick = onClick,
                onIconClick = onIconClick
            )
        } ?: run {
            FilterItem(
                text = hint,
                isActive = false,
                onClick = onClick
            )
        }
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
