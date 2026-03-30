package ru.practicum.android.diploma.feature.filters.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.toDomain
import ru.practicum.android.diploma.core.domain.model.FilterArea
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.Resource
import ru.practicum.android.diploma.feature.filters.domain.FiltersRepository

class FiltersRepositoryImpl(
    private val networkClient: NetworkClient
) : FiltersRepository {
    override fun getAreas(): Flow<Resource<List<FilterArea>>> {
        TODO("Not yet implemented")
    }

    override fun getIndustries(): Flow<Resource<List<FilterIndustry>>> = flow {
        val response = networkClient.doRequest(Request.IndustriesRequest)
        when (response.resultCode) {
            ERROR -> emit(Resource.Error(response.resultCode.toString()))
            SUCCESS -> with(response as IndustriesResponse) {
                emit(
                    Resource.Success(
                        industries.map { it.toDomain() }
                    )
                )
            }

            else -> emit(Resource.Error(response.resultCode.toString()))
        }
    }

    companion object {
        private const val SUCCESS = 200
        private const val ERROR = -1
    }
}
