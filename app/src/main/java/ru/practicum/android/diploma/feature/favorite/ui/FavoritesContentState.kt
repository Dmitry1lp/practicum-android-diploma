package ru.practicum.android.diploma.feature.favorite.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.VacancyItem
import ru.practicum.android.diploma.core.presentation.model.toItemData

@Composable
fun FavoritesContentState(
    vacancies: List<Vacancy>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = vacancies,
            key = { vacancy -> vacancy.id }
        ) { vacancy ->
            val vacancyData = vacancy.toItemData()

            VacancyItem(
                data = vacancyData,
                onClick = { onItemClick(vacancyData.id) }
            )
        }
    }
}

private const val MOCK_LOWER_BOUND = 50000
private const val MOCK_UPPER_BOUND = 100_000
private val mockVacancy = Vacancy(
    id = "0000e405-f9cc-4f28-842a-e70b33d17d42",
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
        "Amazon",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/1200px-Amazon_logo.svg.png"
    ),
    skills = listOf("HTML", "CSS", "SQL", "PHP", "Ruby"),
    website = "cluster-czz5s0kz4scl.eu-west-1.rds.amazonaws.com/vacancies/0000e405-f9cc-4f28-842a-e70b33d17d42",
    industry = "Металлургия, металлообработка"
)

fun createList(count: Int = 10): List<Vacancy> = List(count) { mockVacancy.copy(id = it.toString()) }
