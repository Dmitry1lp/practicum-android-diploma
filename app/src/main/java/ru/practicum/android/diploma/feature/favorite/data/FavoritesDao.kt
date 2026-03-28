package ru.practicum.android.diploma.feature.favorite.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.config.DatabaseConfig

@Dao
interface FavoritesDao {

    @Query(
        """
        SELECT * FROM ${DatabaseConfig.FAVORITE_VACANCY_TABLE}
        ORDER BY addedAtMillis DESC
    """
    )
    fun getVacancies(): Flow<List<FavoriteVacancyEntity>>

    @Query(" SELECT * FROM ${DatabaseConfig.FAVORITE_VACANCY_TABLE} WHERE id = :id")
    suspend fun getVacancy(id: String): FavoriteVacancyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: FavoriteVacancyEntity)

    @Delete
    suspend fun delete(vacancy: FavoriteVacancyEntity)
}
