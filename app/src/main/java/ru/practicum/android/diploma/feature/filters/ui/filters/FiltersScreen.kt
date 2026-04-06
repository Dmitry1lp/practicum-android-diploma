package ru.practicum.android.diploma.feature.filters.ui.filters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.DismissButton
import ru.practicum.android.diploma.feature.filters.ui.SelectableFilterItem

@Composable
fun FiltersScreen(
    state: FiltersUiState,
    modifier: Modifier = Modifier,
    actions: FiltersActions
) {
    BackHandler(enabled = true) { actions.onBackClick() }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_filter_settings),
                onNavigationIcon = actions.onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            val location: String? = state.country?.name?.let { country ->
                "$country, ${state.region?.name}"
            }

            SelectableFilterItem(
                text = location,
                hint = stringResource(R.string.filter_work_location),
                onClick = actions.onWorkLocationFilter,
                onIconClick = { actions.onClearClick(ClearTarget.WorkLocation) }
            )
            SelectableFilterItem(
                text = state.industry?.name,
                hint = stringResource(R.string.filter_industry),
                onClick = actions.onIndustryFilter,
                onIconClick = { actions.onClearClick(ClearTarget.Industry) }
            )

            SalaryInputField(
                text = state.salaryText,
                onTextChange = actions.onSalaryTextChange
            )
            SwitchFilterItem(
                text = stringResource(R.string.checkbox_hide_without_salary),
                checked = state.isCheckBox,
                onCheckedChange = actions.onCheckBox
            )

            Spacer(modifier = Modifier.weight(1f))

            if (true) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
                ) {
                    ApplyButton(
                        text = stringResource(R.string.button_apply),
                        onClick = { actions.onApplyClick(true) }
                    )
                    DismissButton(
                        text = stringResource(R.string.button_reset),
                        onClick = { actions.onClearClick(ClearTarget.All) }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun FiltersScreenPreviewLightMode() {
    DiplomaTheme {
        FiltersScreen(
            state = FiltersUiState(
                country = GeoArea.Country(
                    id = 0,
                    name = "Россия",
                    regions = emptyList()
                ),
                region = GeoArea.Region(
                    id = 0,
                    name = "Москва",
                    countryId = 0
                ),
                isCheckBox = true
            ),
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
