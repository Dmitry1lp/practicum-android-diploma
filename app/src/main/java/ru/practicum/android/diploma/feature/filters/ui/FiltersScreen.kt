package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.feature.filters.presentation.Actions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersUiState

@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier,
    state: FiltersUiState,
    actions: Actions
) {
    when {
        state.onIndustriesScreen -> IndustriesScreen(
            state = state,
            modifier = modifier,
            actions = actions
        )
        else -> FilteringSettingsScreen(
            state = state,
            modifier = modifier.padding(top = AppDimensions.paddingMedium),
            actions = actions
        )
    }
}
