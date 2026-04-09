package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionActions
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.AreasStatus
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationScreenState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterContentState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterFetchErrorState
import ru.practicum.android.diploma.feature.filters.ui.states.FilterNoSuchRegionState

@Composable
fun SelectRegionScreen(
    modifier: Modifier = Modifier,
    state: WorkLocationScreenState,
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
                text = state.regionSearchQuery,
                hint = stringResource(R.string.hint_search_region),
                onTextChange = { actions.onSearchTextChange(it) }
            )
            Box {
                when (state.status) {
                    AreasStatus.Content -> {
                        when {
                            state.isRegionSearchEmpty -> FilterNoSuchRegionState()
                            else -> FilterContentState(
                                items = state.filteredRegions,
                                onItemClick = actions.onRegionClick
                            )
                        }
                    }

                    AreasStatus.FetchError -> FilterFetchErrorState()
                    AreasStatus.Loading -> {}
                }
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@PreviewLightDark
//@Composable
//private fun SelectRegionScreenPreview(
//    @PreviewParameter(SelectRegionScreenStateProvider::class) state: RegionScreenState
//) {
//    DiplomaTheme {
//        SelectRegionScreen(
//            state = state,
//            actions = RegionActions(
//                onRegionClick = {},
//                onSearchTextChange = {},
//                onBackClick = {}
//            )
//        )
//    }
//}
