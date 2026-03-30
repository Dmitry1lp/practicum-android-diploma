package ru.practicum.android.diploma.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

/**
 * Базовый компонент для создания плейсхолдеров.
 */
@Composable
fun StateInfo(
    @DrawableRes image: Int,
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.StateInfo.contentGap)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null
        )
        Text(
            text = text,
            style = AppTypography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = textAlign,
            lineHeight = lineHeight ?: TextUnit.Unspecified
        )
    }
}

@Preview
@Composable
private fun StateInfoPreview() {
    DiplomaTheme {
        StateInfo(
            image = R.drawable.ic_launcher_foreground,
            text = "Такого региона нет"
        )
    }
}

@Preview
@Composable
private fun StateInfoPreviewDark() {
    DiplomaTheme(true) {
        StateInfo(
            image = R.drawable.ic_launcher_foreground,
            text = "Такого региона нет"
        )
    }
}
