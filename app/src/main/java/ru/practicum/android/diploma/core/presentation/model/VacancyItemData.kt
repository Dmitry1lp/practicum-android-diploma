package ru.practicum.android.diploma.core.presentation.model

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.utils.toLocalizedString

data class VacancyItemData(
    val id: String,
    val name: String,
    val location: String,
    val industry: String,
    val salary: String,
    val imageUrl: String?
)

@Composable
fun Vacancy.toItemData(): VacancyItemData = VacancyItemData(
    id = id,
    name = name,
    location = address?.city ?: "",
    industry = industry,
    salary = salary.toLocalizedString(),
    imageUrl = employer.logoUrl
)
