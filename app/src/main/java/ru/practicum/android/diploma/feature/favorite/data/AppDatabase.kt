package ru.practicum.android.diploma.feature.favorite.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [FavoriteVacancyEntity::class]
)
@TypeConverters(StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesDao
}
