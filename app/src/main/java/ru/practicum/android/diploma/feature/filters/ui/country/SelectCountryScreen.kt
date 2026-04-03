package ru.practicum.android.diploma.feature.filters.ui.country

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.country.SelectCountryUiState
import ru.practicum.android.diploma.feature.filters.ui.states.FetchErrorState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterContentState

@Composable
fun SelectCountryScreen(
    modifier: Modifier = Modifier,
    state: SelectCountryUiState,
    onBackClick: () -> Unit,
    onCountryClick: (GeoArea) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_country),
                onNavigationIcon = onBackClick
            )
        }
    ) { innerPaddings ->
        Box(
            Modifier
                .padding(innerPaddings)
                .padding(vertical = AppDimensions.paddingMedium)
        ) {
            when (state) {
                is SelectCountryUiState.Content -> FilterContentState(
                    items = state.countries,
                    onItemClick = onCountryClick
                )

                is SelectCountryUiState.FetchError -> FetchErrorState()
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun SelectCountryScreenPreview(
    @PreviewParameter(SelectCountryUiStateProvider::class) state: SelectCountryUiState
) {
    DiplomaTheme {
        SelectCountryScreen(
            state = state,
            onBackClick = {},
            onCountryClick = {}
        )
    }
}
