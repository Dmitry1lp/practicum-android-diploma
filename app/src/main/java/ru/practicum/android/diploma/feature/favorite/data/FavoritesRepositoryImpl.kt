package ru.practicum.android.diploma.feature.favorite.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.data.database.dao.FavoritesDao
import ru.practicum.android.diploma.core.data.database.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override suspend fun getVacancy(id: String): Vacancy? = favoritesDao.getVacancy(id)?.toDomain()

    override suspend fun insert(vacancy: Vacancy) = favoritesDao.insert(vacancy.toEntity())

    override suspend fun delete(id: String) {
        favoritesDao.getVacancy(id)?.let { vacancyEntity ->
            favoritesDao.delete(vacancyEntity)
        }
    }

    override suspend fun isFavorite(id: String): Boolean = getVacancy(id) != null

    override fun getVacancies(): Flow<List<Vacancy>> = favoritesDao.getVacancies().toDomain()

    private fun Flow<List<FavoriteVacancyEntity>>.toDomain() = map { entities ->
        entities.map { vacancyEntity ->
            vacancyEntity.toDomain()
        }
    }

    private fun Vacancy.toEntity(): FavoriteVacancyEntity = FavoriteVacancyEntity(
        id = id,
        name = name,
        description = description,
        salary = salary,
        address = address,
        experience = experience,
        schedule = schedule,
        employmentType = employmentType,
        contacts = contacts,
        employer = employer,
        skills = skills,
        website = website,
        industry = industry
    )

    private fun FavoriteVacancyEntity.toDomain(): Vacancy = Vacancy(
        id = id,
        name = name,
        description = description,
        salary = salary,
        address = address,
        experience = experience,
        schedule = schedule,
        employmentType = employmentType,
        contacts = contacts,
        employer = employer,
        skills = skills,
        website = website,
        industry = industry
    )

}
