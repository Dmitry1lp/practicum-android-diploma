package ru.practicum.android.diploma.feature.filters.ui.worklocation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.primaryButtonColors
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationActions
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState
import ru.practicum.android.diploma.feature.filters.ui.SelectableFilterItem

/**
 * Экран выбора места работы (страны и региона).
 *
 * Отображает два поля для выбора: страна и регион.
 * Пользователь может последовательно выбрать страну, затем регион внутри неё.
 * Кнопка "Применить" становится активной только при наличии изменений.
 *
 * @param modifier Модификатор для внешнего позиционирования
 * @param currentState Текущее состояние экрана (выбранные страна и регион)
 * @param initState Исходное состояние, с которым сравниваются изменения (обычно пришло из сохранённых фильтров)
 * @param actions Колбэки для обработки действий пользователя:
 *   - [WorkLocationActions.onBackClick] - возврат на предыдущий экран
 *   - [WorkLocationActions.onCountryClick] - переход к выбору страны
 *   - [WorkLocationActions.onRegionClick] - переход к выбору региона
 *   - [WorkLocationActions.onApplyClick] - сохранение выбранного места работы
 */
@Composable
fun WorkLocationScreen(
    modifier: Modifier = Modifier,
    currentState: WorkLocationUiState,
    initState: WorkLocationUiState,
    actions: WorkLocationActions,
) {
    val isApplyButtonEnabled = currentState != initState

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_work_location),
                onNavigationIcon = actions.onBackClick
            )
        }
    ) { innerPaddings ->
        val locationFields = listOf(
            Triple(currentState.country, R.string.filter_country, actions.onCountryClick),
            Triple(currentState.region, R.string.filter_region, actions.onRegionClick)
        )

        Column(
            modifier = Modifier.padding(innerPaddings)
        ) {
            locationFields.forEach { (area, hintId, action) ->
                SelectableFilterItem(
                    text = area?.name,
                    hint = stringResource(hintId),
                    onSelectedClick = action,
                    onUnselectedClick = action
                )
            }

            Spacer(Modifier.weight(1f))

            if (isApplyButtonEnabled) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppDimensions.FiltersScreen.heightButton)
                        .padding(horizontal = AppDimensions.paddingMedium),
                    shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                    colors = primaryButtonColors(),
                    onClick = actions.onApplyClick
                ) {
                    Text(
                        text = stringResource(R.string.button_apply),
                        style = AppTypography.titleSmall
                    )
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun WorkLocationScreenPreview(
    @PreviewParameter(WorkLocationUiStateProvider::class) states: Pair<WorkLocationUiState, WorkLocationUiState>
) {
    DiplomaTheme {
        WorkLocationScreen(
            currentState = states.first,
            initState = states.second,
            actions = WorkLocationActions(
                onBackClick = { },
                onCountryClick = { },
                onRegionClick = { },
                onApplyClick = { }
            )
        )
    }
}
