package ru.practicum.android.diploma.feature.vacancy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.utils.toLocalizedString
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyCardData

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

        // название вакансии
        item {
            Text(
                text = vacancy.name,
                style = AppTypography.titleLarge
            )
        }

        // зарплата
        item{
            Text(
                text = vacancy.salary.toLocalizedString(),
                style = AppTypography.titleMedium
            )
        }

        // Card компании
        item {
            VacancyCard(
                modifier = Modifier.padding(top = AppDimensions.paddingMedium),
                data = VacancyCardData(
                    logoUrl = vacancy.employer.logoUrl,
                    industry = vacancy.industry,
                    location = vacancy.address?.city
                )
            )
        }

        // название компании
        item {
            // заголовок
            Text(
                modifier = Modifier.padding(top = AppDimensions.paddingMedium),
                text = stringResource(R.string.vacancy_company_title),
                style = AppTypography.titleMedium
            )

            Text(
                text = vacancy.employer.name,
                style = AppTypography.bodyLarge
            )
        }

        // адрес
        vacancy.address?.let { address ->
            item {
                // заголовок
                Text(
                    modifier = Modifier.padding(top = AppDimensions.paddingMedium),
                    text = stringResource(R.string.vacancy_address),
                    style = AppTypography.titleMedium
                )
                Text(
                    text = listOfNotNull(
                        address.city,
                        address.street,
                        address.building
                    ).joinToString(", "),
                    style = AppTypography.bodyLarge
                )
            }
        }

        // условия
        val experience = vacancy.experience?.takeIf { it.isNotBlank() }
        val employmentType = vacancy.employmentType?.takeIf { it.isNotBlank() }
        val schedule = vacancy.schedule?.takeIf { it.isNotBlank() }

        if (experience != null || employmentType != null || schedule != null) {
            // заголовок условий
            item {
                Text(
                    modifier = Modifier.padding(top = AppDimensions.paddingMedium),
                    text = stringResource(R.string.vacancy_required_experience),
                    style = AppTypography.titleSmall
                )
            }

            item {
                Column(
                    //verticalArrangement = Arrangement.spacedBy(8.dp) // !!!!
                ) {

                    // experience
                    experience?.let {
                        Text(
                            text = it,
                            style = AppTypography.bodyMedium
                        )
                    }

                    // employmentType + schedule в строке
                    val employmentType = vacancy.employmentType?.takeIf { it.isNotBlank() }
                    val schedule = vacancy.schedule?.takeIf { it.isNotBlank() }

                    val rowText = listOfNotNull(
                        employmentType,
                        schedule
                    ).joinToString(". ")

                    if (rowText.isNotBlank()) {
                        Text(
                            text = rowText,
                            style = AppTypography.bodyMedium
                        )
                    }
                }
            }
        }

        // заголовок описания вакансии
        item {
            Text(
                modifier = Modifier.padding(top = AppDimensions.paddingVeryBig),
                text = stringResource(R.string.vacancy_description),
                style = AppTypography.titleMedium
            )
        }

        // описание вакансии
        item {
            Text(
                modifier = Modifier.padding(top = AppDimensions.paddingMedium),
                text = vacancy.description,
                style = AppTypography.bodyLarge
            )
        }

        // навыки
        if (vacancy.skills.isNotEmpty()) {

            // заголовок ключевые навыки
            item {
                Text(
                    modifier = Modifier.padding(top = AppDimensions.paddingBig),
                    text = stringResource(R.string.vacancy_key_skills),
                    style = AppTypography.titleMedium
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(top = AppDimensions.paddingMedium)
                ) {
                    vacancy.skills.forEach { skill ->
                        Row {
                            Text(
                                text = "•",
                                style = AppTypography.bodyMedium,
                                modifier = Modifier.padding(end = 6.dp)
                            )
                            Text(
                                text = skill,
                                style = AppTypography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // контакты
        vacancy.contacts?.let { contacts ->

            // заголовок контакты
            item {
                Text(
                    modifier = Modifier.padding(top = AppDimensions.paddingBig),
                    text = stringResource(R.string.vacancy_contacts),
                    style = AppTypography.titleMedium
                )
            }

            item {
                Column(

                ) {

                    Text(
                        text = contacts.name,
                        style = AppTypography.bodyLarge
                    )

                    contacts.email.let { email ->
                        Text(
                            text = email,
                            style = AppTypography.bodyLarge,
                            modifier = Modifier.clickable {
                                onEmailClick(email)
                            }
                        )
                    }

                    contacts.phoneNumbers.forEach { phone ->
                        Text(
                            text = phone,
                            style = AppTypography.bodyLarge,
                            modifier = Modifier.clickable {
                                onPhoneClick(phone)
                            }
                        )
                    }
                }
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
fun VacancyContentStatePreview() {
    DiplomaTheme {
        VacancyContentState(
            vacancy = mockVacancy,
            onPhoneClick = {},
            onEmailClick = {}
        )
    }
}

