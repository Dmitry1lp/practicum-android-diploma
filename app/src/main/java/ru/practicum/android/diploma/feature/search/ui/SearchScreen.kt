package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        topBar = { SearchTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Поиск
            SearchBar(
                text = state.searchText,
                isClearVisible = state.isClearTextVisible,
                onTextChange = viewModel::onSearchTextChanged,
                onClearClick = viewModel::onClearTextClicked
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Chip — всегда под поиском
            val vacanciesCount = (state.vacancyState as? VacancyState.Content)?.vacancies?.size ?: 0
            val chipText = if (vacanciesCount > 0) {
                pluralStringResource(R.plurals.vacancies_found, vacanciesCount, vacanciesCount)
            } else {
                stringResource(R.string.no_vacancies_found)
            }

            Chip(text = chipText)

            Spacer(modifier = Modifier.height(8.dp))

            // Контент: список или плейсхолдер
            Box(modifier = Modifier.fillMaxSize()) {
                when (val stateContent = state.vacancyState) {
                    VacancyState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                    is VacancyState.Content -> VacancyList(
                        vacancies = stateContent.vacancies,
                        onVacancyClick = onVacancyClick,
                        modifier = Modifier.fillMaxSize()
                    )

                    VacancyState.Empty -> NotFoundPlaceholder(modifier = Modifier.fillMaxSize())

                    VacancyState.ErrorInternet -> NotInternetPlaceholder(modifier = Modifier.fillMaxSize())

                    VacancyState.ErrorFound -> NotFoundPlaceholder(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
