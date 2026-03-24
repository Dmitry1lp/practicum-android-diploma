package ru.practicum.android.diploma.core.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.core.data.database.dao.FavoritesDao
import ru.practicum.android.diploma.core.data.database.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
}
