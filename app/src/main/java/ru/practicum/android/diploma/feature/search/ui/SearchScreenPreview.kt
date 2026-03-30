package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Vacancy

@Composable
fun SearchScreenPreview(
    state: SearchUiState,
    onVacancyClick: (Vacancy) -> Unit = {}
) {
    Scaffold(
        topBar = { SearchTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchBar(
                text = state.searchText,
                isClearVisible = state.isClearTextVisible,
                onTextChange = {},
                onClearClick = {}
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val stateContent = state.vacancyState) {
                    VacancyState.Loading -> CircularProgressIndicator()
                    is VacancyState.Content -> Column(modifier = Modifier.fillMaxSize()) {
                        val vacanciesText = pluralStringResource(
                            id = R.plurals.vacancies_found,
                            count = stateContent.vacancies.size,
                            stateContent.vacancies.size
                        )

                        Chip(text = vacanciesText)

                        Spacer(modifier = Modifier.height(8.dp))
                        VacancyList(vacancies = stateContent.vacancies, onVacancyClick = onVacancyClick)
                    }

                    VacancyState.Empty -> {
                        if (state.searchText.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_search_screen),
                                contentDescription = "Search Placeholder"
                            )
                        }
                    }

                    VacancyState.ErrorInternet -> NoInternetPlaceholder()
                    VacancyState.ErrorFound -> NotFoundPlaceholder()
                    VacancyState.Idle -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenEmpty() {
    val state = SearchUiState(searchText = "", vacancyState = VacancyState.Empty)
    DiplomaTheme { SearchScreenPreview(state = state) }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenLoading() {
    val state = SearchUiState(searchText = "Android", vacancyState = VacancyState.Loading)
    DiplomaTheme { SearchScreenPreview(state = state) }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenContent() {
    val fakeEmployer = Employer(
        name = "Компания XYZ",
        logoUrl = "android.resource://ru.practicum.android.diploma/drawable/ic_preview_search_screen"
    )

    val vacancies = listOf(
        Vacancy(
            id = "1",
            name = "Android Developer",
            description = "Разработка Android приложений",
            salary = null,
            address = null,
            experience = null,
            schedule = null,
            employmentType = null,
            contacts = null,
            employer = fakeEmployer,
            skills = emptyList(),
            website = "",
            industry = "IT"
        ),
        Vacancy(
            id = "2",
            name = "Kotlin Developer",
            description = "Разработка на Kotlin",
            salary = null,
            address = null,
            experience = null,
            schedule = null,
            employmentType = null,
            contacts = null,
            employer = fakeEmployer,
            skills = emptyList(),
            website = "",
            industry = "IT"
        )
    )

    val state = SearchUiState(
        searchText = "Android",
        vacancyState = VacancyState.Content(vacancies)
    )

    DiplomaTheme { SearchScreenPreview(state = state) }
}
