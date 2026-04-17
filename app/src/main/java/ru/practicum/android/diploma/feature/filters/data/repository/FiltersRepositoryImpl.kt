package ru.practicum.android.diploma.feature.filters.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.GeoAreasResponse
import ru.practicum.android.diploma.core.data.network.dto.IndustriesResponse
import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.toGeoArea
import ru.practicum.android.diploma.core.data.network.dto.toIndustries
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.data.model.FiltersSettings
import ru.practicum.android.diploma.feature.filters.domain.repository.FiltersRepository

class FiltersRepositoryImpl(
    private val networkClient: NetworkClient,
    private val sharedPrefs: SharedPreferences
) : FiltersRepository {
    override fun getCountries(): Flow<Result<List<GeoArea.Country>>> = flow {
        val response = networkClient.doRequest(Request.AreasRequest)
        val result = when (response.resultCode) {
            ERROR -> Result.failure(Exception(response.resultCode.toString()))
            SUCCESS -> {
                val data = (response as GeoAreasResponse)
                    .geoAreas
                    .map { it.toGeoArea() }
                    .sortedBy { it.name }
                    .filterIsInstance<GeoArea.Country>()

                Result.success(data)
            }

            else -> Result.failure(Exception(response.resultCode.toString()))
        }

        emit(result)
    }

    override fun getIndustries(): Flow<Result<List<FilterIndustry>>> = flow {
        val response = networkClient.doRequest(Request.IndustriesRequest)
        val result = when (response.resultCode) {
            ERROR -> Result.failure(Exception(response.resultCode.toString()))
            SUCCESS -> {
                val data = (response as IndustriesResponse)
                    .industries
                    .map { it.toIndustries() }

                Result.success(data)
            }

            else -> Result.failure(Exception(response.resultCode.toString()))
        }
        emit(result)
    }

    override fun getFiltersSettings(): FiltersSettings? {
        return if (sharedPrefs.contains(FILTERS_SETTINGS_KEY)) {
            val json = sharedPrefs.getString(FILTERS_SETTINGS_KEY, "")
            Gson().fromJson(json, FiltersSettings::class.java)
        } else {
            null
        }
    }

    override fun saveFiltersSetting(settings: FiltersSettings) {
        sharedPrefs.edit { putString(FILTERS_SETTINGS_KEY, Gson().toJson(settings)) }
    }

    override fun clearSettings() {
        sharedPrefs.edit { remove(FILTERS_SETTINGS_KEY) }
    }

    companion object {
        private const val SUCCESS = 200
        private const val ERROR = -1
        private const val FILTERS_SETTINGS_KEY = "filters_settings_key"
    }
}
