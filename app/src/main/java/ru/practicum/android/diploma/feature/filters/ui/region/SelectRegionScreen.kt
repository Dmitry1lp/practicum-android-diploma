package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState
import ru.practicum.android.diploma.feature.filters.ui.FilterItem
import ru.practicum.android.diploma.feature.filters.ui.states.FetchErrorState

@Composable
fun SelectRegionScreen(
    modifier: Modifier = Modifier,
    state: FiltersUiState,
    onSearchTextChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
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
                text = state.searchText,
                hint = stringResource(R.string.hint_search_region),
                onTextChange = { onSearchTextChange(it) }
            )
            if (state.industries.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = AppDimensions.paddingSmall)
                ) {
                    items(
                        items = state.industries,
                        key = { industry -> industry.id }
                    ) { industry ->
                        FilterItem(
                            text = industry.name,
                            onClick = { TODO("Implement") },
                            isActive = true
                        )
                    }
                }
            } else {
                FetchErrorState()
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun IndustriesScreenPreviewLightMode() {
    DiplomaTheme(false) {
        SelectRegionScreen(
            state = FiltersUiState(),
            onSearchTextChange = {},
            onBackClick = {},
        )
    }
}
