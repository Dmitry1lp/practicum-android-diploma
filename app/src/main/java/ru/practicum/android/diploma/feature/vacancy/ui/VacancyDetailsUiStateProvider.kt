package ru.practicum.android.diploma.feature.vacancy.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.presentation.preview.VacancyPreviewData
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsUiState

class VacancyDetailsUiStateProvider : PreviewParameterProvider<VacancyDetailsUiState> {

    override val values: Sequence<VacancyDetailsUiState>
        get() = sequenceOf(
            VacancyDetailsUiState.Loading,
            VacancyDetailsUiState.NetworkError,
            VacancyDetailsUiState.NotFound,
            VacancyDetailsUiState.ServerError,
            VacancyDetailsUiState.Content(
                vacancy = VacancyPreviewData.vacancy,
                isFavorite = true
            )
        )
}
