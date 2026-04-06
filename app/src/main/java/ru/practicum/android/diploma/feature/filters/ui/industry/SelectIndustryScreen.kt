package ru.practicum.android.diploma.feature.filters.ui.industry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryScreenState
import ru.practicum.android.diploma.feature.filters.presentation.industry.IndustryUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.filters.RadioButtonItem
import ru.practicum.android.diploma.feature.filters.ui.states.FilterFetchErrorState

@Composable
fun SelectIndustryScreen(
    modifier: Modifier = Modifier,
    screenState: IndustryScreenState,
    initSelectedIndustry: FilterIndustry?,
    onIndustryClick: (FilterIndustry) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onApplyClick: (FilterIndustry?) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_industry),
                onNavigationIcon = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            val isApplyButtonEnabled = initSelectedIndustry != screenState.selectedIndustry

            AppSearchBar(
                text = screenState.searchText,
                hint = stringResource(R.string.hint_search_industry),
                onTextChange = { onSearchTextChanged(it) }
            )

            when (val stateContent = screenState.uiState) {
                is IndustryUiState.Content -> with(screenState) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        ShowContent(
                            industries = stateContent.filteredIndustries,
                            selectedIndustry = selectedIndustry,
                            onSelectionChange = onIndustryClick
                        )
                    }

                    if (isApplyButtonEnabled) {
                        ApplyButton(
                            modifier = Modifier.padding(bottom = AppDimensions.paddingBig),
                            text = stringResource(R.string.button_choose),
                            onClick = { onApplyClick(selectedIndustry) }
                        )
                    }
                }

                IndustryUiState.FetchError -> FilterFetchErrorState()
                IndustryUiState.Loading -> {}
            }
        }
    }
}

@Composable
private fun ShowContent(
    industries: List<FilterIndustry>,
    selectedIndustry: FilterIndustry?,
    onSelectionChange: (FilterIndustry) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            keyboardController?.hide()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimensions.paddingSmall),
        state = listState
    ) {
        items(
            items = industries,
            key = { it.id }
        ) { industry ->
            RadioButtonItem(
                text = industry.name,
                isSelected = industry == selectedIndustry
            ) {
                onSelectionChange(industry)
                keyboardController?.hide()
            }
        }
    }
}
