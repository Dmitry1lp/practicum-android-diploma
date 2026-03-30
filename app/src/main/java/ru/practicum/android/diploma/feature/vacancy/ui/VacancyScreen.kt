package ru.practicum.android.diploma.feature.vacancy.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.TopBarIcon
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyUiEvent
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyUiState
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel

@Composable
fun VacancyScreen(
    state: VacancyUiState,
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
                is VacancyUiState.Content,
                is VacancyUiState.NotFound -> {
                    AppTopBar(
                        title = stringResource(R.string.screen_vacancy),
                        onNavigationIcon = onBackClick,
                        action1 = TopBarIcon(
                            iconResId = R.drawable.ic_sharing_24,
                            onClick = onShareClick
                        ),
                        action2 = TopBarIcon(
                            iconResId = if ((state as? VacancyUiState.Content)?.isFavorite == true)
                                R.drawable.ic_favorites_on_24
                            else
                                R.drawable.ic_favorites_off_24,
                            onClick = onFavouriteClick
                        )
                    )
                }

                is VacancyUiState.Loading,
                is VacancyUiState.ServerError -> {
                    AppTopBar(
                        title = stringResource(R.string.screen_vacancy),
                        onNavigationIcon = onBackClick
                    )
                }

                else -> {}
            }
        }
    ) { paddingValues ->

        Surface(modifier = Modifier.padding(paddingValues)) {

            when (state) {

                VacancyUiState.Loading -> VacancyLoadingIndicator()

                VacancyUiState.NotFound -> VacancyEmptyState()

                VacancyUiState.ServerError -> VacancyErrorState()

                is VacancyUiState.Content -> {
                    VacancyContentState(
                        vacancy = state.vacancy,
                        onPhoneClick = onPhoneClick,
                        onEmailClick = onEmailClick
                    )
                }

                else -> {}
            }
        }
    }
}
