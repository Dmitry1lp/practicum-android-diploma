package ru.practicum.android.diploma.feature.filters.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.toDomain
import ru.practicum.android.diploma.core.domain.model.Industry
import ru.practicum.android.diploma.core.domain.model.Resource
import ru.practicum.android.diploma.feature.filters.domain.FiltersRepository

class FiltersRepositoryImpl(
    private val networkClient: NetworkClient
) : FiltersRepository {
    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.doRequest(Request.IndustriesRequest)
        when (response.resultCode) {
            -1 -> emit(Resource.Error(response.resultCode.toString()))
            200 -> with(response as IndustriesResponse) {
                emit(
                    Resource.Success(
                        industries.map {it.toDomain()}
                    )
                )
            }

            else -> emit(Resource.Error(response.resultCode.toString()))
        }
    }
}
