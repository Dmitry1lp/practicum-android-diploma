package ru.practicum.android.diploma.feature.favorite.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.favorite.presentation.FavoritesUiState

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_favorites)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state) {
                is FavoritesUiState.Content -> FavoritesContentState(
                    vacancies = state.vacancies,
                    onItemClick = onVacancyClick
                )

                is FavoritesUiState.Empty -> FavoritesEmptyState()
                is FavoritesUiState.FetchError -> FavoritesFetchErrorState()
                is FavoritesUiState.Loading -> {}
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun FavoritesScreenPreview(
    @PreviewParameter(FavoritesUiStateProvider::class) state: FavoritesUiState
) {
    DiplomaTheme {
        FavoritesScreen(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onVacancyClick = {}
        )
    }
}
