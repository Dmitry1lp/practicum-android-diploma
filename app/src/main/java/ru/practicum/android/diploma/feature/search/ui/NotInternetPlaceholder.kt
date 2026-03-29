package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.StateInfo

@Composable
fun NoInternetPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        StateInfo(
            image = R.drawable.ic_placeholder_not_internet,
            text = stringResource(R.string.error_no_internet)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetPlaceholderPreview() {
    DiplomaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NoInternetPlaceholder()
        }
    }
}
