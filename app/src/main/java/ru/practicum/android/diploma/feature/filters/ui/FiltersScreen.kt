package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.feature.filters.presentation.FiltersState

@Composable
fun FiltersScreen(
    stateViewModel: MutableStateFlow<FiltersState>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSwitchClick: () -> Unit,
//    onBranchScreen: () -> Unit
) {
    var onIndustriesScreen by remember { mutableStateOf(false) }
//    val state by stateViewModel.collectAsStateWithLifecycle()

    when {
        onIndustriesScreen -> IndustriesScreen(
            stateViewModel = stateViewModel,
            modifier = modifier,
            onBackClick = { onIndustriesScreen = !onIndustriesScreen }
        )
        else -> FilteringSettingsScreen(
            stateViewModel = stateViewModel,
            modifier = modifier.padding(top = AppDimensions.paddingMedium),
            onBackClick = onBackClick,
            onIndustriesScreenNavigate = { onIndustriesScreen = !onIndustriesScreen },
            onSwitchClick = onSwitchClick
        )
    }
}
