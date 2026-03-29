package ru.practicum.android.diploma.feature.vacancy.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.TopBarIcon
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyUiState

@Composable
fun VacancyScreenContent(
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

private const val MOCK_LOWER_BOUND = 50000
private const val MOCK_UPPER_BOUND = 100_000

private val mockVacancy = Vacancy(
    id = "1",
    name = "Product Manager в Amazon",
    description = "Приглашаем на вакансию Product Manager в Amazon",
    salary = Salary(
        MOCK_LOWER_BOUND,
        MOCK_UPPER_BOUND,
        "RUB"
    ),
    address = Address(
        "Уфа",
        "Ленина",
        "9"
    ),
    experience = "Нет опыта",
    schedule = "Полный день",
    employmentType = "Полная занятость",
    contacts = Contacts(
        "Смирнов Алексей Иванович",
        "123@gmail.com",
        listOf("+7 (999) 456-78-90", "+7 (999) 654-32-10")
    ),
    employer = Employer(
        name = "Amazon",
        logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/1200px-Amazon_logo.svg.png"
    ),
    skills = listOf("HTML", "CSS", "SQL", "PHP", "Ruby"),
    website = "https://example.com",
    industry = "Металлургия, металлообработка"
)

@Preview(showBackground = true)
@Composable
fun VacancyScreenContentPreview_Content() {
    DiplomaTheme {
        VacancyScreenContent(
            state = VacancyUiState.Content(
                vacancy = mockVacancy, // создаёшь фейковые данные
                isFavorite = true
            ),
            onBackClick = {},
            onFavouriteClick = {},
            onShareClick = {},
            onPhoneClick = {},
            onEmailClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun VacancyScreenContentPreview_Loading() {
    VacancyScreenContent(
        state = VacancyUiState.Loading,
        onBackClick = {},
        onFavouriteClick = {},
        onShareClick = {},
        onPhoneClick = {},
        onEmailClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun VacancyScreenContentPreview_Error() {
    VacancyScreenContent(
        state = VacancyUiState.ServerError,
        onBackClick = {},
        onFavouriteClick = {},
        onShareClick = {},
        onPhoneClick = {},
        onEmailClick = {}
    )
}
