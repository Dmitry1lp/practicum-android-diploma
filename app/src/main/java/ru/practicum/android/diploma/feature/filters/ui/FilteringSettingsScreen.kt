package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.Red
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.Actions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState

@Composable
fun FilteringSettingsScreen(
    state: FiltersUiState,
    modifier: Modifier = Modifier,
    actions: Actions
) {
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
            InactiveFilterItem(
                text = stringResource(R.string.filter_industry),
                onClick = actions.onIndustriesScreen
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
            if (state.salaryText.isNotEmpty() || state.isCheckBox) {
                Column(
                    modifier = Modifier.padding(horizontal = 17.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppDimensions.FiltersScreen.heightButton),
                        shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                        onClick = {}
                    ) {
                        Text(
                            text = stringResource(R.string.button_apply),
                            style = AppTypography.titleSmall
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(AppDimensions.FiltersScreen.resetButtonPadding)
                            .align(Alignment.CenterHorizontally)
                            .clickable(onClick = {
                                if (state.isCheckBox) actions.onCheckBox()
                                actions.onSalaryTextChange("")
                            }),
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
private fun FilteringSettingsScreenPreviewLightMode() {
    DiplomaTheme(false) {
        FilteringSettingsScreen(
            state = FiltersUiState(),
            actions = Actions()
        )
    }
}

@Preview
@Composable
private fun FilteringSettingsScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        FilteringSettingsScreen(
            state = FiltersUiState(),
            actions = Actions()
        )
    }
}
