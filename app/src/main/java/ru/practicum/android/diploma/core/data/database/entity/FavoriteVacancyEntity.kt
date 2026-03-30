package ru.practicum.android.diploma.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.core.config.DatabaseConfig
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Salary

@Entity(tableName = DatabaseConfig.FAVORITE_VACANCY_TABLE)
data class FavoriteVacancyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    @Embedded(prefix = "salary_") val salary: Salary?,
    @Embedded(prefix = "address_") val address: Address?,
    val experience: String?,
    val schedule: String?,
    val employmentType: String?,
    @Embedded(prefix = "contacts_") val contacts: Contacts?,
    @Embedded(prefix = "employer_") val employer: Employer,
    val skills: List<String>,
    val website: String,
    val industry: String,
    val addedAtMillis: Long = System.currentTimeMillis()
)
