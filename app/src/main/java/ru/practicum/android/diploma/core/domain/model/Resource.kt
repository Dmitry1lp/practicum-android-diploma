package ru.practicum.android.diploma.core.domain.model

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: String) : Resource<T>
}
