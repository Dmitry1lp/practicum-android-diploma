package ru.practicum.android.diploma.app.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface FiltersRoute : NavKey {

    @Serializable
    data object Main : FiltersRoute

    @Serializable
    data object Industry : FiltersRoute

    @Serializable
    data object WorkLocation : FiltersRoute

    @Serializable
    data object Country : FiltersRoute

    @Serializable
    data object Region : FiltersRoute
}
