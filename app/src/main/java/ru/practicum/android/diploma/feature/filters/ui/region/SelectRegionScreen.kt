package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.region.SelectRegionUiState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterContentState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterFetchErrorState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterNoSuchRegionState

@Composable
fun SelectRegionScreen(
    modifier: Modifier = Modifier,
    state: SelectRegionUiState,
    onRegionClick: (GeoArea) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_region),
                onNavigationIcon = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            AppSearchBar(
                text = searchText,
                hint = stringResource(R.string.hint_search_region),
                onTextChange = {
                    searchText = it
                    onSearchTextChange(it)
                }
            )
            Box {
                when (state) {
                    is SelectRegionUiState.Content -> FilterContentState(
                        items = state.regions,
                        onItemClick = onRegionClick
                    )

                    is SelectRegionUiState.FetchError -> FilterFetchErrorState()
                    is SelectRegionUiState.NoSuchRegion -> FilterNoSuchRegionState()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun SelectRegionScreenPreview(
    @PreviewParameter(SelectRegionUiStateProvider::class) state: SelectRegionUiState
) {
    DiplomaTheme {
        SelectRegionScreen(
            state = state,
            onRegionClick = {},
            onSearchTextChange = {},
            onBackClick = {}
        )
    }
}
