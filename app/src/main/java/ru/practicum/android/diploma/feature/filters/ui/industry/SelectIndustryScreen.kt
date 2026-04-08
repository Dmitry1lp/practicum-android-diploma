package ru.practicum.android.diploma.feature.filters.ui.industry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryActions
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.states.FilterFetchErrorState

@Composable
fun SelectIndustryScreen(
    modifier: Modifier = Modifier,
    screenState: IndustryScreenState,
    actions: IndustryActions
) {
    val isApplyButtonEnabled = screenState.selectedIndustry != null

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_industry),
                onNavigationIcon = actions.onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            AppSearchBar(
                text = screenState.searchText,
                hint = stringResource(R.string.hint_search_industry),
                onTextChange = { actions.onSearchTextChanged(it) }
            )

            when (val stateContent = screenState.uiState) {
                is IndustryUiState.Content -> with(screenState) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        IndustrySelectionList(
                            industries = stateContent.filteredIndustries,
                            selectedIndustry = selectedIndustry,
                            onSelectionChange = actions.onIndustryClick
                        )
                    }

                    if (isApplyButtonEnabled) {
                        ApplyButton(
                            modifier = Modifier.padding(bottom = AppDimensions.paddingBig),
                            text = stringResource(R.string.button_choose),
                            onClick = { actions.onApplyClick(selectedIndustry) }
                        )
                    }
                }

                IndustryUiState.FetchError -> FilterFetchErrorState()
                IndustryUiState.Loading -> {}
            }
        }
    }
}
