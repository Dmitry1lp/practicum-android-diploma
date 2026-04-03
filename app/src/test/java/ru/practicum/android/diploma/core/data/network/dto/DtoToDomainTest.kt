package ru.practicum.android.diploma.core.data.network.dto

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import ru.practicum.android.diploma.app.di.dataModule
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.domain.model.GeoArea
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DtoToDomainTest : KoinTest {

    private lateinit var api: VacancyApi

    @Before
    fun setup() {
        stopKoin()
        startKoin { modules(dataModule) }
        api = get()
    }

    @Test
    fun vacancyDtoToDomain() = runBlocking {
        val result = api.searchVacancies()
        val vacancyDto = result.items.first()
        val vacancy = vacancyDto.toDomain()

        assertEquals(vacancyDto.description, vacancy.description)
        assertEquals(vacancyDto.address?.raw, vacancy.address?.fullAddress)
        assertEquals(vacancyDto.url, vacancy.website)
        assertEquals(
            vacancyDto.contacts?.phones?.first()?.formatted,
            vacancy.contacts?.phoneNumbers?.first()
        )
    }

    @Test
    fun areaDtoToDomain() = runBlocking {
        val result = api.getAreas()
        val filterAreaDto = result.first()
        val country = filterAreaDto.toGeoArea() as GeoArea.Country

        assertEquals(country.id, country.regions.first().countryId)
        assertNotEquals(country.name, country.regions.first().name)
    }

}
