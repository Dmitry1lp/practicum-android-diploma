@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions.paddingMedium
import ru.practicum.android.diploma.app.ui.theme.AppDimensions.paddingVeryBig
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar

@Composable
fun TeamScreen(
    modifier: Modifier = Modifier
) {
    val developers = stringArrayResource(R.array.developers)

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.nav_team)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.about_team),
                style = AppTypography.titleLarge
            )

            LazyColumn(
                modifier = Modifier.padding(top = paddingVeryBig)
            ) {
                items(developers) { item ->
                    Text(
                        modifier = Modifier.padding(bottom = paddingMedium),
                        text = item,
                        style = AppTypography.titleSmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TeamScreenPreviewLightMode() {
    DiplomaTheme(false) {
        TeamScreen()
    }
}

@Preview
@Composable
private fun TeamScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        TeamScreen()
    }
}
