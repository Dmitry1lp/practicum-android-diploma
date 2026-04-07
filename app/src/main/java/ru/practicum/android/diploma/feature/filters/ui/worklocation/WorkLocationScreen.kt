package ru.practicum.android.diploma.feature.filters.ui.worklocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.ClearTarget
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationActions
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationUiState
import ru.practicum.android.diploma.feature.filters.ui.ApplyButton
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
    actions: WorkLocationActions,
) {
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
                    onClick = action,
                    onIconClick = {
                        when (area) {
                            is GeoArea.Country -> actions.onClearClick(ClearTarget.Country)
                            is GeoArea.Region -> actions.onClearClick(ClearTarget.Region)
                            else -> {}
                        }

                    }
                )
            }

            Spacer(Modifier.weight(1f))

            currentState.country?.let {
                ApplyButton(
                    modifier = Modifier.padding(bottom = AppDimensions.paddingBig),
                    text = stringResource(R.string.button_apply),
                    onClick = { actions.onApplyClick(currentState) },
                )
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
            actions = WorkLocationActions(
                onBackClick = { },
                onCountryClick = { },
                onRegionClick = { },
                onClearClick = { },
                onApplyClick = { }
            )
        )
    }
}
