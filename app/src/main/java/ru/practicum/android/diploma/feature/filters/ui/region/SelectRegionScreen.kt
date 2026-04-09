package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionActions
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionScreenState
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionUiState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterContentState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterFetchErrorState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterNoSuchRegionState

@Composable
fun SelectRegionScreen(
    modifier: Modifier = Modifier,
    screenState: RegionScreenState,
    actions: RegionActions
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_region),
                onNavigationIcon = actions.onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            AppSearchBar(
                text = screenState.searchText,
                hint = stringResource(R.string.hint_search_region),
                onTextChange = { actions.onSearchTextChange(it) }
            )
            Box {
                when (val state = screenState.uiState) {
                    is RegionUiState.Content -> FilterContentState(
                        items = state.filteredRegions,
                        onItemClick = actions.onRegionClick
                    )

                    is RegionUiState.FetchError -> FilterFetchErrorState()
                    is RegionUiState.NoResult -> FilterNoSuchRegionState()
                    is RegionUiState.Loading -> {}
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun SelectRegionScreenPreview(
    @PreviewParameter(SelectRegionScreenStateProvider::class) state: RegionScreenState
) {
    DiplomaTheme {
        SelectRegionScreen(
            screenState = state,
            actions = RegionActions(
                onRegionClick = {},
                onSearchTextChange = {},
                onBackClick = {}
            )
        )
    }
}
