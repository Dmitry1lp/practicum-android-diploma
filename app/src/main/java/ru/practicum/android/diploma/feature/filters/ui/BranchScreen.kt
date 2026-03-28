package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar

@Composable
fun BranchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_industry),
                onNavigationIcon = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {

        }
    }
}

@Preview
@Composable
private fun BranchScreenPreviewLightMode() {
    DiplomaTheme(false) {
        BranchScreen(
            onBackClick = {},
        )
    }
}

@Preview
@Composable
private fun BranchScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        BranchScreen(
            onBackClick = {},
        )
    }
}
