package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.TopBarIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    isFiltersSettings: Boolean,
    onFiltersClick: () -> Unit
) {
    AppTopBar(
        title = stringResource(R.string.screen_vacancy_search),
        action1 = TopBarIcon(
            iconResId = if (isFiltersSettings) {
                R.drawable.ic_filter_on_24
            } else {
                R.drawable.ic_filter_off_24
            },
            contentDescription = stringResource(R.string.cd_filter),
            onClick = onFiltersClick
        )
    )
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun SearchTopBarPreviewLight() {
    DiplomaTheme {
        Surface {
            SearchTopBar(false) {}
        }
    }
}
