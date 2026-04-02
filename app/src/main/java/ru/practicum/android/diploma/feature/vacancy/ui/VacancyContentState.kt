package ru.practicum.android.diploma.feature.vacancy.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyAddress
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyCompanyCard
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyCompanyInfo
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyConditions
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyContacts
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyDescription
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancyHeader
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancySalary
import ru.practicum.android.diploma.feature.vacancy.ui.components.VacancySkills

@Composable
fun VacancyContentState(
    vacancy: Vacancy,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(AppDimensions.paddingMedium),
    ) {
        item { VacancyHeader(vacancy) }
        item { VacancySalary(vacancy) }
        item { VacancyCompanyCard(vacancy) }
        item { VacancyCompanyInfo(vacancy) }

        vacancy.address?.let {
            item { VacancyAddress(it) }
        }

        item { VacancyConditions(vacancy) }
        item { VacancyDescription(vacancy) }

        if (vacancy.skills.isNotEmpty()) {
            item { VacancySkills(vacancy.skills) }
        }

        vacancy.contacts?.let {
            item {
                VacancyContacts(
                    contacts = it,
                    onPhoneClick = onPhoneClick,
                    onEmailClick = onEmailClick
                )
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

@Preview(showBackground = true, heightDp = 1200)
@Composable
private fun VacancyContentStatePreview() {
    DiplomaTheme {
        VacancyContentState(
            vacancy = mockVacancy,
            onPhoneClick = {},
            onEmailClick = {}
        )
    }
}
