package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.tertiaryButtonColors

@Composable
fun DismissButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(AppDimensions.FiltersScreen.heightButton)
            .padding(horizontal = AppDimensions.FiltersScreen.paddingButton),
        shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
        colors = tertiaryButtonColors(),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = AppTypography.titleSmall
        )
    }
}

@PreviewLightDark
@Composable
internal fun DismissButtonPreview() {
    DiplomaTheme {
        DismissButton(
            text = "Сбросить"
        ) { }
    }
}
