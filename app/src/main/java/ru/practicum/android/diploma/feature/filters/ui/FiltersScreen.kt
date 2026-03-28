package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.AppDimensions

@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var onBranchScreen by remember { mutableStateOf(false) }

    when {
        onBranchScreen -> BranchScreen(
            modifier = modifier,
            onBackClick = { onBranchScreen = !onBranchScreen }
        )
        else -> FilteringSettingsScreen(
            modifier = modifier.padding(
                top = AppDimensions.paddingMedium
            ),
            onBackClick = onBackClick,
            onBranchScreenNavigate = { onBranchScreen = !onBranchScreen }
        )
    }
}
