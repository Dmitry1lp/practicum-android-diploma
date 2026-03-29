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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.Red
import ru.practicum.android.diploma.feature.filters.presentation.FiltersState

@Composable
fun FilteringSettingsScreen(
    stateViewModel: MutableStateFlow<FiltersState>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onIndustriesScreenNavigate: () -> Unit,
    onSwitchClick: () -> Unit
) {
    val state by stateViewModel.collectAsStateWithLifecycle()
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_filter_settings),
                onNavigationIcon = onBackClick
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
                onClick = onIndustriesScreenNavigate
            )
            SalaryInputField(
                text = text,
                onTextChange = { text = it }
            )
            SwitchFilterItem(
                text = stringResource(R.string.checkbox_hide_without_salary),
                checked = state.isWithoutSalary,
                onCheckedChange = onSwitchClick
            )
            Spacer(modifier = Modifier.weight(1f))
            if (text.isNotEmpty()) {
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
                            .clickable(onClick = { text = "" }),
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
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {},
            onIndustriesScreenNavigate = {},
            onSwitchClick = {}
        )
    }
}

@Preview
@Composable
private fun FilteringSettingsScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        FilteringSettingsScreen(
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {},
            onIndustriesScreenNavigate = {},
            onSwitchClick = {}
        )
    }
}
