package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen() {
    val developers = stringArrayResource(R.array.developers)

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(64.dp),
                title = {
                    Text(
                        text = stringResource(R.string.nav_team),
                        style = AppTypography.titleMedium
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.about_team),
                style = AppTypography.titleLarge
            )

            LazyColumn(
                modifier = Modifier.padding(top = 32.dp)
            ) {
                items(developers) { item ->
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
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
