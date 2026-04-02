package ru.practicum.android.diploma.feature.filters.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.core.domain.model.Resource

class FiltersInteractorImpl(
    private val repository: FiltersRepository
) : FiltersInteractor {
    override fun getIndustries(): Flow<Pair<List<FilterIndustry>?, String?>> {
        return repository.getIndustries().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}
