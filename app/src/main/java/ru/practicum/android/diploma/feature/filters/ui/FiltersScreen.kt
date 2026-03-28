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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FiltersScreen(
    stateViewModel: MutableStateFlow<FiltersState>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
//    onBranchScreen: () -> Unit
) {
    var onBranchScreen by remember { mutableStateOf(false) }
//    val state by stateViewModel.collectAsStateWithLifecycle()

    when {
        onBranchScreen -> BranchScreen(
            modifier = modifier,
            onBackClick = { onBranchScreen = !onBranchScreen }
        )
        else -> FilteringSettingsScreen(
            stateViewModel = stateViewModel,
            modifier = modifier.padding(
                top = AppDimensions.paddingMedium
            ),
            onBackClick = onBackClick,
            onBranchScreenNavigate = { onBranchScreen = !onBranchScreen }
        )
    }
}
