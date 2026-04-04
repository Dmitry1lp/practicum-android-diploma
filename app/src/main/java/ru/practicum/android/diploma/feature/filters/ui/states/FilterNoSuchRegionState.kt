package ru.practicum.android.diploma.feature.filters.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.components.StateInfo

@Composable
fun FilterNoSuchRegionState(
    modifier: Modifier = Modifier
) {
    StateInfo(
        modifier = modifier,
        image = R.drawable.img_fetch_error,
        text = stringResource(R.string.error_region_not_found)
    )
}
