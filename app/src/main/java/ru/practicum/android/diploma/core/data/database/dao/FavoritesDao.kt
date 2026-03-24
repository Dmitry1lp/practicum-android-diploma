package ru.practicum.android.diploma.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.core.data.database.entity.FavoriteVacancyEntity

@Dao
interface FavoritesDao {

    // добавить в избранное
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: FavoriteVacancyEntity)

    // удалить
    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteById(id: String)

    // получить список избранных вакансий
    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<FavoriteVacancyEntity>

    // получить по id
    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getById(id: String): FavoriteVacancyEntity?
}
