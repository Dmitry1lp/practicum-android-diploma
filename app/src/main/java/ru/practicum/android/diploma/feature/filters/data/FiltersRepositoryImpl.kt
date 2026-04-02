package ru.practicum.android.diploma.feature.filters.data

import android.content.SharedPreferences
import com.google.gson.Gson
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
import ru.practicum.android.diploma.feature.filters.domain.FiltersSettings

class FiltersRepositoryImpl(
    private val networkClient: NetworkClient,
    private val sharedPrefs: SharedPreferences
) : FiltersRepository {
    override fun getAreas(): Flow<Resource<List<FilterArea>>> {
        TODO("Not yet implemented")
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

    override fun getFiltersSetting(): FiltersSettings? {
        if (sharedPrefs.contains(FILTERS_SETTINGS_KEY)) {
            val json = sharedPrefs.getString(FILTERS_SETTINGS_KEY, "")
            val filtersSettings = Gson().fromJson(json, FiltersSettings::class.java)
            return filtersSettings
        } else {
            return null
        }
    }

    companion object {
        private const val SUCCESS = 200
        private const val ERROR = -1
        private const val FILTERS_SETTINGS_KEY = "filters_settings_key"
    }
}
