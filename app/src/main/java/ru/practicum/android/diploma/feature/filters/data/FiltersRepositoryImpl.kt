package ru.practicum.android.diploma.feature.filters.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.FilterAreaDto
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.toDomain
import ru.practicum.android.diploma.core.data.network.dto.toGeoArea
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.core.domain.model.Resource
import ru.practicum.android.diploma.feature.filters.domain.FiltersRepository

class FiltersRepositoryImpl(
    private val networkClient: NetworkClient
) : FiltersRepository {
    override fun getAreas(): Flow<Resource<List<GeoArea.Country>>> = flow {
        val response = networkClient.doRequest(Request.AreasRequest)

        val result = when (response.resultCode) {
            ERROR -> Resource.Error(response.resultCode.toString())
            SUCCESS -> {
                val dtoList = response as? List<FilterAreaDto>

                if (dtoList != null) {
                    val countries = dtoList
                        .filter { it.parentId == null} // по условию задачи
                        .map { it.toGeoArea() as GeoArea.Country }

                    Resource.Success(countries)
                } else {
                    Resource.Error("Empty response")
                }
            }

            else -> Resource.Error(response.resultCode.toString())
        }

        emit(result)
    }

    override fun getIndustries(): Flow<Resource<List<FilterIndustry>>> = flow {
        val response = networkClient.doRequest(Request.IndustriesRequest)
        val resource = when (response.resultCode) {
            ERROR -> Resource.Error(response.resultCode.toString())
            SUCCESS -> with(response as IndustriesResponse) {
                Resource.Success(industries.map { it.toDomain() })
            }

            else -> Resource.Error(response.resultCode.toString())
        }
        emit(resource)
    }

    companion object {
        private const val SUCCESS = 200
        private const val ERROR = -1
    }
}
