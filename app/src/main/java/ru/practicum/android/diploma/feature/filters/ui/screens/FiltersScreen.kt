package ru.practicum.android.diploma.feature.filters.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.Red
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.Clear
import ru.practicum.android.diploma.feature.filters.presentation.FiltersActions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
import ru.practicum.android.diploma.feature.filters.ui.HintedFilterItem
import ru.practicum.android.diploma.feature.filters.ui.InactiveFilterItem
import ru.practicum.android.diploma.feature.filters.ui.SalaryInputField
import ru.practicum.android.diploma.feature.filters.ui.SwitchFilterItem

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
            InactiveFilterItem(
                text = stringResource(R.string.filter_work_location),
                onClick = {}
            )
            if (state.industry == null) {
                InactiveFilterItem(
                    text = stringResource(R.string.filter_industry),
                    onClick = actions.onIndustryFilter
                )
            } else {
                HintedFilterItem(
                    hint = stringResource(R.string.filter_industry),
                    text = state.industry.name,
                    onClick = actions.onIndustryFilter,
                    onIconClick = { actions.onClearClick(Clear.Industry) }
                )
            }
            SalaryInputField(
                text = state.salaryText,
                onTextChange = actions.onSalaryTextChange
            )
            SwitchFilterItem(
                text = stringResource(R.string.checkbox_hide_without_salary),
                checked = state.onCheckBox,
                onCheckedChange = actions.onCheckBox
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.salaryText.isNotEmpty() ||
                state.onCheckBox ||
                state.industry != null
            ) {
                Column {
                    ApplyButton(
                        text = stringResource(R.string.button_apply),
                        onClick = { actions.onApplyClick(true) }
                    )
                    Text(
                        modifier = Modifier
                            .padding(AppDimensions.FiltersScreen.resetButtonPadding)
                            .align(Alignment.CenterHorizontally)
                            .clickable(onClick = { actions.onClearClick(Clear.All) }),
                        text = stringResource(R.string.button_reset),
                        style = AppTypography.titleSmall,
                        color = Red
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FiltersScreenPreviewLightMode() {
    DiplomaTheme(false) {
        FiltersScreen(
            state = FiltersUiState(),
            actions = FiltersActions()
        )
    }
}

@Preview
@Composable
private fun FiltersScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        FiltersScreen(
            state = FiltersUiState(
                industry = FilterIndustry(0, "IT"),
                onCheckBox = true
            ),
            actions = FiltersActions()
        )
    }
}
