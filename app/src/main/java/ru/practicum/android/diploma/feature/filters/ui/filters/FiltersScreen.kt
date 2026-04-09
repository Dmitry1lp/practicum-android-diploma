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
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.filters.FiltersUiState
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocation
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.DismissButton
import ru.practicum.android.diploma.feature.filters.ui.SelectableFilterItem

@Composable
fun FiltersScreen(
    currentState: FiltersUiState,
    areButtonsEnabled: Boolean,
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
            SelectableFilterItem(
                text = currentState.workLocation.locationString,
                hint = stringResource(R.string.filter_work_location),
                onClick = actions.onWorkLocationClick,
                onIconClick = { actions.onClearClick(ClearTarget.WorkLocation) }
            )
            SelectableFilterItem(
                text = currentState.industry?.name,
                hint = stringResource(R.string.filter_industry),
                onClick = actions.onIndustryClick,
                onIconClick = { actions.onClearClick(ClearTarget.Industry) }
            )

            SalaryInputField(
                text = currentState.salaryText,
                onTextChange = actions.onSalaryTextChange
            )
            SwitchFilterItem(
                text = stringResource(R.string.checkbox_hide_without_salary),
                checked = currentState.isCheckBox,
                onCheckedChange = actions.onCheckBoxChange
            )

            Spacer(modifier = Modifier.weight(1f))

            if (areButtonsEnabled) {
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
    val region = GeoArea.Region(
        id = 1,
        name = "Москва",
        countryId = 0
    )
    val country = GeoArea.Country(
        id = 0,
        name = "Россия",
        regions = listOf(region)
    )

    DiplomaTheme {
        FiltersScreen(
            currentState = FiltersUiState(
                workLocation = WorkLocation(country, region),
                isCheckBox = true
            ),
            areButtonsEnabled = true,
            actions = FiltersActions(
                onBackClick = { },
                onWorkLocationClick = { },
                onIndustryClick = { },
                onSalaryTextChange = { },
                onCheckBoxChange = { },
                onApplyClick = { },
                onClearClick = { }
            )
        )
    }
}
