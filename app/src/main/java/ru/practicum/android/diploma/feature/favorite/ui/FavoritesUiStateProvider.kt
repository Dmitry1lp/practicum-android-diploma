package ru.practicum.android.diploma.feature.favorite.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.presentation.preview.VacancyPreviewData
import ru.practicum.android.diploma.feature.favorite.presentation.FavoritesUiState

class FavoritesUiStateProvider : PreviewParameterProvider<FavoritesUiState> {

    private companion object {
        private const val VACANCIES_SHOWN = 20
    }

    override val values: Sequence<FavoritesUiState>
        get() = sequenceOf(
            FavoritesUiState.Loading,
            FavoritesUiState.Empty,
            FavoritesUiState.FetchError,
            FavoritesUiState.Content(
                vacancies = VacancyPreviewData.list(VACANCIES_SHOWN)
            )
        )

}
