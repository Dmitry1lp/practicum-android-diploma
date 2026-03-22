package ru.practicum.android.diploma.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Search : Route

    @Serializable
    data object Filters : Route

    @Serializable
    data object Favorites : Route

    @Serializable
    data class Vacancy(val id: String) : Route

    @Serializable
    data object Team : Route

}
