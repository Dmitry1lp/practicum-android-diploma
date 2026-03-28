package ru.practicum.android.diploma.feature.favorite.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
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

@Preview(showSystemUi = true)
@Composable
private fun FavoritesScreenPreview() {
    DiplomaTheme {
        FavoritesScreen(
            state = FavoritesUiState.Content(createList(20)),
            modifier = Modifier.fillMaxSize(),
            onVacancyClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FavoritesScreenPreviewDark() {
    DiplomaTheme(true) {
        FavoritesScreen(
            state = FavoritesUiState.FetchError,
            modifier = Modifier.fillMaxSize(),
            onVacancyClick = {}
        )
    }
}
