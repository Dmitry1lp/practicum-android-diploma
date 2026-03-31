package ru.practicum.android.diploma.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteVacancyEntity(

    @PrimaryKey
    val id: String,
    val name: String,
    val employer: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val city: String,
    val url: String
)
