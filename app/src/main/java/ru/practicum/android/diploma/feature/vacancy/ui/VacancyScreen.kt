package ru.practicum.android.diploma.feature.vacancy.ui

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
import ru.practicum.android.diploma.core.presentation.components.TopBarIcon
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsUiState

@Composable
fun VacancyScreen(
    state: VacancyDetailsUiState,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            when (state) {
                is VacancyDetailsUiState.Content,
                is VacancyDetailsUiState.NotFound -> {
                    AppTopBar(
                        title = stringResource(R.string.screen_vacancy),
                        onNavigationIcon = onBackClick,
                        action1 = TopBarIcon(
                            iconResId = R.drawable.ic_sharing_24,
                            onClick = onShareClick
                        ),
                        action2 = TopBarIcon(
                            iconResId = if ((state as? VacancyDetailsUiState.Content)?.isFavorite == true) {
                                R.drawable.ic_favorites_on_24
                            } else {
                                R.drawable.ic_favorites_off_24
                            },
                            onClick = onFavouriteClick
                        )
                    )
                }

                is VacancyDetailsUiState.Loading,
                is VacancyDetailsUiState.ServerError,
                is VacancyDetailsUiState.NetworkError -> {
                    AppTopBar(
                        title = stringResource(R.string.screen_vacancy),
                        onNavigationIcon = onBackClick
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            when (state) {
                VacancyDetailsUiState.Loading -> VacancyLoadingIndicator()

                VacancyDetailsUiState.NotFound -> VacancyEmptyState()

                VacancyDetailsUiState.ServerError -> VacancyErrorState()

                VacancyDetailsUiState.NetworkError -> VacancyErrorState()

                is VacancyDetailsUiState.Content -> {
                    VacancyContentState(
                        vacancy = state.vacancy,
                        onPhoneClick = onPhoneClick,
                        onEmailClick = onEmailClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1200)
@PreviewLightDark
@Composable
private fun VacancyScreenContentPreview_Content(
    @PreviewParameter(VacancyDetailsUiStateProvider::class) state: VacancyDetailsUiState
) {
    DiplomaTheme {
        VacancyScreen(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            onFavouriteClick = {},
            onShareClick = {},
            onPhoneClick = {},
            onEmailClick = {}
        )
    }
}
