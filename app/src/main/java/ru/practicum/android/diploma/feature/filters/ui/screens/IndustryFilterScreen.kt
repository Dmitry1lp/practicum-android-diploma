package ru.practicum.android.diploma.feature.filters.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.Blue
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.StateInfo
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState
import ru.practicum.android.diploma.feature.filters.ui.ActivateButton

@Composable
fun IndustryFilterScreen(
    modifier: Modifier = Modifier,
    state: FiltersUiState,
    actions: FiltersActions
) {
    var selectedIndustry by remember { mutableStateOf<FilterIndustry?>(null) }

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
                onTextChange = { actions.onSearchTextChange(it) }
            )
            if (state.industries.isNotEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    ShowContent(
                        state.industries,
                        selectedIndustry = selectedIndustry,
                        onSelectionChange = { newIndustry ->
                            selectedIndustry = newIndustry
                        }
                    )
                }
                selectedIndustry?.let {
                    ActivateButton(
                        text = stringResource(R.string.button_choose),
                        onClick = { actions.onIndustrySelected(it) }
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimensions.paddingSmall)
    ) {
        items(industries) { industry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.LabelActionListItem.itemHeight)
                    .selectable(
                        selected = industry == selectedIndustry,
                        onClick = { onSelectionChange(industry) }
                    )
                    .padding(horizontal = AppDimensions.paddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = AppDimensions.paddingMedium)
                        .weight(1f),
                    text = industry.name,
                    style = AppTypography.bodyLarge
                )
                RadioButton(
                    selected = industry == selectedIndustry,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Blue,
                        unselectedColor = Blue
                    )
                )
            }
        }
    }
}


@Preview
@Composable
private fun IndustryFilterScreenPreviewLightMode() {
    DiplomaTheme(false) {
        IndustryFilterScreen(
            state = FiltersUiState(),
            actions = FiltersActions()
        )
    }
}

@Preview
@Composable
private fun IndustryFilterScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        IndustryFilterScreen(
            state = FiltersUiState(),
            actions = FiltersActions()
        )
    }
}
