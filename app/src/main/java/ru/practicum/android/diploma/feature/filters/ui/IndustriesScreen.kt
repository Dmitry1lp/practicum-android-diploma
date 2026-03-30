package ru.practicum.android.diploma.feature.filters.ui

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
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.Actions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState

@Composable
fun IndustriesScreen(
    modifier: Modifier = Modifier,
    state: FiltersUiState,
    actions: Actions
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_industry),
                onNavigationIcon = actions.onIndustriesScreen
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            SearchInputField(
                text = state.searchText,
                onTextChange = { actions.onSearchTextChange(it) }
            )
            if (state.industries.isNotEmpty()) {
                ShowContent(state.industries)
            } else {
                PlaceholderErrorListFetch()
            }
        }
    }
}

@Composable
private fun ShowContent(industries: List<FilterIndustry>) {
    var selectedOption by remember { mutableStateOf<FilterIndustry?>(null) }

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
                        selected = (industry == selectedOption),
                        onClick = { selectedOption = industry }
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
                    selected = (industry == selectedOption),
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
private fun IndustriesScreenPreviewLightMode() {
    DiplomaTheme(false) {
        IndustriesScreen(
            state = FiltersUiState(),
            actions = Actions()
        )
    }
}

@Preview
@Composable
private fun IndustriesScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        IndustriesScreen(
            state = FiltersUiState(),
            actions = Actions()
        )
    }
}
