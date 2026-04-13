package ru.practicum.android.diploma.feature.search.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.StateInfo
import ru.practicum.android.diploma.feature.search.presentation.SearchEvent
import ru.practicum.android.diploma.feature.search.presentation.SearchUiState
import ru.practicum.android.diploma.feature.search.presentation.VacancyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    onSearchTextChanged: (String) -> Unit,
    onVacancyClick: (Vacancy) -> Unit,
    onLoadNextPage: () -> Unit,
    onFiltersClick: () -> Unit,
    onAction: () -> Unit,
    getFiltersSettings: () -> Unit,
    events: Flow<SearchEvent>
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val internetErrorText = stringResource(R.string.search_no_internet)
    val commonErrorText = stringResource(R.string.search_error)

    LaunchedEffect(Unit) {
        events.collect { event ->

            val message = when (event) {
                SearchEvent.ShowInternetError -> internetErrorText
                SearchEvent.ShowCommonError -> commonErrorText
            }

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            getFiltersSettings()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(),
        topBar = {
            SearchTopBar(
                isFiltersSettings = state.filtersSettings != null
            ) { onFiltersClick() }
        }
    ) { paddingValues ->
        val chipText = getChipText(state.vacancyState, state.totalFound)

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchBar(
                text = state.searchText,
                onTextChange = onSearchTextChanged,
                onAction = onAction
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                // Контент: список или плейсхолдер
                when (val stateContent = state.vacancyState) {
                    VacancyState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                    is VacancyState.Content -> VacancyList(
                        vacancies = stateContent.vacancies.toImmutableList(),
                        onVacancyClick = onVacancyClick,
                        onLoadNextPage = onLoadNextPage,
                        isLoadingNextPage = state.isNextPageLoading,
                        modifier = Modifier.fillMaxSize()
                    )

                    VacancyState.Idle -> {
                        StateInfo(
                            image = R.drawable.img_search_screen
                        )
                    }

                    VacancyState.Empty -> NotFoundPlaceholder(modifier = Modifier.fillMaxSize())

                    VacancyState.ErrorInternet -> NotInternetPlaceholder(modifier = Modifier.fillMaxSize())

                    VacancyState.ErrorFound -> NotFoundPlaceholder(modifier = Modifier.fillMaxSize())
                }

                chipText?.let { text ->
                    Chip(
                        text = text,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun getChipText(
    vacancyState: VacancyState,
    totalFound: Int
): String? = when (vacancyState) {
    is VacancyState.Content -> {
        pluralStringResource(
            R.plurals.vacancies_found,
            totalFound,
            totalFound
        )
    }

    is VacancyState.Empty,

    is VacancyState.ErrorFound -> stringResource(R.string.no_vacancies_found)
    VacancyState.ErrorInternet,
    VacancyState.Loading,
    VacancyState.Idle -> null
}
