package ru.practicum.android.diploma.app.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.R

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Search : Route, BottomNavItem {
        override val icon: Int = R.drawable.ic_main_24
        override val label: Int = R.string.nav_home
    }

    @Serializable
    data object Favorites : Route, BottomNavItem {
        override val icon: Int = R.drawable.ic_favorite_filled_24
        override val label: Int = R.string.nav_favorites
    }

    @Serializable
    data object Team : Route, BottomNavItem {
        override val icon: Int = R.drawable.ic_team_24
        override val label: Int = R.string.nav_team
    }

    @Serializable
    data object Filters : Route

    @Serializable
    data class Vacancy(val id: String) : Route

}
