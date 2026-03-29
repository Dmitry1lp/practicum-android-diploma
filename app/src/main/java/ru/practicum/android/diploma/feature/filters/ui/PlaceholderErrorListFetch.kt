package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.feature.filters.presentation.FiltersState

@Composable
fun PlaceholderErrorListFetch() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.paddingMedium)
                .padding(top = 122.dp),
            painter = painterResource(R.drawable.error_list_fetch),
            contentDescription = "error_list_fetch",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .width(268.dp)
                .padding(top = AppDimensions.paddingMedium),
            text = stringResource(R.string.error_list_fetch),
            style = AppTypography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PlaceholderErrorListFetchLightMode() {
    DiplomaTheme(false) {
        PlaceholderErrorListFetch()
    }
}

@Preview
@Composable
private fun PlaceholderErrorListFetchDarkMode() {
    DiplomaTheme(true) {
        PlaceholderErrorListFetch()
    }
}
