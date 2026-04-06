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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.StateInfo
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.filters.RadioButtonItem

@Composable
fun IndustryFilterScreen(
    modifier: Modifier = Modifier,
    state: FiltersUiState,
    actions: FiltersActions
) {
    var selectedIndustry by remember(state.industry) {
        mutableStateOf(state.industry)
    }

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
                text = state.searchText,
                hint = stringResource(R.string.hint_search_industry),
                onTextChange = { actions.onTextChange(it) }
            )
            if (state.filteredIndustries.isNotEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    ShowContent(
                        state.filteredIndustries,
                        selectedIndustry = selectedIndustry,
                        onSelectionChange = { newIndustry ->
                            selectedIndustry = newIndustry
                        }
                    )
                }
                selectedIndustry?.let {
                    ApplyButton(
                        modifier = Modifier.padding(bottom = AppDimensions.paddingBig),
                        text = stringResource(R.string.button_choose),
                        onClick = { actions.onApplyClick(it) }
                    )
                }
            } else {
                StateInfo(
                    image = R.drawable.img_error_list_fetch,
                    text = stringResource(R.string.error_list_fetch),
                    isPaddingBottom = true
                )
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

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun IndustryFilterScreenPreviewLightMode() {
    DiplomaTheme {
        IndustryFilterScreen(
            state = FiltersUiState(),
            actions = FiltersActions(
                onBackClick = { },
                onWorkLocationFilter = { },
                onIndustryFilter = { },
                onSalaryTextChange = { },
                onCheckBox = { },
                onSearchTextChange = { },
                onApplyClick = { },
                onClearClick = { }
            )
        )
    }
}
