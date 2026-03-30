package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.model.Vacancy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onVacancyClick: (Vacancy) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchTopBar()
            SearchBar(
                text = state.searchText,
                isClearVisible = state.isClearTextVisible,
                onTextChange = viewModel::onSearchTextChanged,
                onClearClick = viewModel::onClearTextClicked
            )
        }
    ) { paddingValues ->
        val chipText = getChipText(state.vacancyState)

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Контент: список или плейсхолдер
            when (val stateContent = state.vacancyState) {
                VacancyState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is VacancyState.Content -> VacancyList(
                    vacancies = stateContent.vacancies,
                    onVacancyClick = onVacancyClick,
                    modifier = Modifier.fillMaxSize()
                )

                VacancyState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_search_screen),
                            contentDescription = null
                        )
                    }
                }

                VacancyState.Empty -> NotFoundPlaceholder(modifier = Modifier.fillMaxSize())

                VacancyState.ErrorInternet -> NoInternetPlaceholder(modifier = Modifier.fillMaxSize())

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

@Composable
private fun getChipText(vacancyState: VacancyState): String? = when (vacancyState) {
    is VacancyState.Content -> {
        val vacanciesCount = vacancyState.vacancies.size
        pluralStringResource(R.plurals.vacancies_found, vacanciesCount, vacanciesCount)
    }

    is VacancyState.Empty -> stringResource(R.string.no_vacancies_found)

    VacancyState.ErrorFound,
    VacancyState.ErrorInternet,
    VacancyState.Loading,
    VacancyState.Idle -> null
}
