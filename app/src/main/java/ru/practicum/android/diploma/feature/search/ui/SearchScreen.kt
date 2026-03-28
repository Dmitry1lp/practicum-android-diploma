package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
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
            // сам поиск
            SearchBar(
                text = state.searchText,
                isClearVisible = state.isClearTextVisible,
                onTextChange = viewModel::onSearchTextChanged,
                onClearClick = viewModel::onClearTextClicked
            )

            Spacer(modifier = Modifier.height(8.dp))

            // контент
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val stateContent = state.vacancyState) {
                    VacancyState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is VacancyState.Content -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            val vacanciesText = pluralStringResource(
                                id = R.plurals.vacancies_found,
                                count = stateContent.vacancies.size,
                                stateContent.vacancies.size
                            )

                            Chip(text = vacanciesText)

                            Spacer(modifier = Modifier.height(8.dp))
                            VacancyList(vacancies = stateContent.vacancies, onVacancyClick = onVacancyClick)
                        }
                    }

                    VacancyState.Empty -> {
                        if (state.searchText.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_search_screen),
                                contentDescription = "Search Placeholder"
                            )
                        }
                    }

                    VacancyState.ErrorInternet -> {
                        NoInternetPlaceholder()
                    }

                    VacancyState.ErrorFound -> {
                        NotFoundPlaceholder()
                    }
                }
            }
        }
    }
}


