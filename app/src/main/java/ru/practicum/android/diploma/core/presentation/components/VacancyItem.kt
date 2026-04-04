package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.immutableListOf
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.model.VacancyItemData
import ru.practicum.android.diploma.core.presentation.model.toItemData
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

@Composable
fun VacancyItem(
    data: VacancyItemData,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val textItems = remember {
        immutableListOf(
            "${data.name}, ${data.location}" to AppTypography.titleMedium,
            data.industry to AppTypography.bodyLarge,
            data.salary to AppTypography.bodyLarge
        )
    }

    ThumbnailListItem(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(AppDimensions.VacancyItem.contentPadding)
            .antiRepetitionClick { onClick(data.id) },
        model = data.imageUrl,
        imageBorder = BorderStroke(
            width = AppDimensions.VacancyItem.imageBorderWidth,
            color = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            Modifier.padding(start = AppDimensions.VacancyItem.contentGap)
        ) {
            textItems.forEach { (text, style) ->
                Text(
                    text = text,
                    style = style,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
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

@Preview
@Composable
private fun VacancyItemPreview() {
    DiplomaTheme {
        VacancyItem(
            data = mockVacancy.toItemData(),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun VacancyItemPreviewDark() {
    DiplomaTheme(true) {
        VacancyItem(
            data = mockVacancy.toItemData(),
            onClick = {}
        )
    }
}
