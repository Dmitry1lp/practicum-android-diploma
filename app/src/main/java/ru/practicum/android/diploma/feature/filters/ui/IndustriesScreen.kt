package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Industry
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.presentation.FiltersState

@Composable
fun IndustriesScreen(
    stateViewModel: MutableStateFlow<FiltersState>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val state by stateViewModel.collectAsStateWithLifecycle()

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
            if (state.industries.isNotEmpty()) {
                ShowIndustries(state.industries)
            }
        }
    }
}

@Composable
private fun ShowIndustries(industries: List<Industry>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            industries,
            key = { it.id.toString() }
        ) { industry ->
            InactiveFilterItem(
                text = industry.name,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun IndustriesScreenPreviewLightMode() {
    DiplomaTheme(false) {
        IndustriesScreen(
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun IndustriesScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        IndustriesScreen(
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {}
        )
    }
}
