package ru.practicum.android.diploma.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Search : Route, NavKey

    @Serializable
    data object Filters : Route, NavKey

    @Serializable
    data object Favorites : Route, NavKey

    @Serializable
    data class Vacancy(val id: String) : Route, NavKey

    @Serializable
    data object Team : Route, NavKey

}
