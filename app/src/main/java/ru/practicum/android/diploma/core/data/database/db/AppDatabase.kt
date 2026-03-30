package ru.practicum.android.diploma.core.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.core.data.database.dao.FavoritesDao
import ru.practicum.android.diploma.core.data.database.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.feature.favorite.data.StringListTypeConverter

@Database(
    version = 1,
    entities = [FavoriteVacancyEntity::class]
)
@TypeConverters(StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
