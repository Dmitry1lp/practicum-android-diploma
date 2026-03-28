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
import kotlin.test.assertEquals

class VacancyDetailDtoToDomainTest : KoinTest {

    private lateinit var api: VacancyApi

    @Before
    fun setup() {
        stopKoin()
        startKoin { modules(dataModule) }
        api = get()
    }

    @Test
    fun toDomain() = runBlocking {
        val result = api.searchVacancies()
        val vacancyDto = result.items.first()
        val vacancy = vacancyDto.toDomain()

        assertEquals(vacancyDto.description, vacancy.description)
        assertEquals(vacancyDto.address?.raw, vacancy.address?.fullAddress)
        assertEquals(vacancyDto.url, vacancy.website)
        assertEquals(vacancyDto.contacts?.phones?.first()?.formatted, vacancy.contacts?.phoneNumbers?.first())
    }

}
